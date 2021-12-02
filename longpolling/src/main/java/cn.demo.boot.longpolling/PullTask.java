package cn.demo.boot.longpolling;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * pull任务
 * 1. 启动pull任务时将任务实例添加到{@link PullTask}队列中
 * 2. 在任务超时后将实例从队列中移除
 *
 * @author wyl
 * @since 2021-09-18 13:13:08
 */
@Slf4j
public class PullTask implements Runnable {
    // 保存所有拉取请求实例
    private Queue<PullTask> pullTasks;
    // 任务调度服务
    private ScheduledExecutorService scheduler;
    // http异步上下文
    private AsyncContext asyncContext;
    // 数据标识
    private String dataId;
    // md5加密数据
    private String md5Data;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    Future<?> asyncTimeoutFuture;

    public PullTask(Queue<PullTask> pullTasks, ScheduledExecutorService scheduler,
                    AsyncContext asyncContext, String dataId, String md5Data, HttpServletRequest req, HttpServletResponse resp) {
        this.pullTasks = pullTasks;
        this.scheduler = scheduler;
        this.asyncContext = asyncContext;
        this.dataId = dataId;
        this.md5Data = md5Data;
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void run() {
        // 尝试推送数据           ·
        LongPollingService.getInstance().push(dataId, LongPollingDataRepo.get(dataId));

        // 25秒后把当前任务实例从队列中移除调度任务（请求超时处理调度任务）
        asyncTimeoutFuture = scheduler.schedule(() -> {
            //log.info("25秒后开始执行长轮询任务");
            //这里如果remove this会失败,内部类中的this指向的并非当前对象,而是匿名内部类对象
            pullTasks.remove(PullTask.this);
            sendResponse(LongPollingDataRepo.get(dataId));
        }, 25, TimeUnit.SECONDS);
        // 添加当前任务到队列中
        pullTasks.add(this);
    }

    /**
     * 发送响应
     *
     * @param result
     */
    public void sendResponse(String result) {
        log.info("发送响应数据到客户端 客户端:{} 数据:{}", ServletUtil.getClientIP(req), result);
        //取消等待执行的任务,避免已经响完了,还有资源被占用
        if (asyncTimeoutFuture != null) {
            // 设置为true会立即中断执行中的任务,false对执行中的任务无影响,但会取消等待执行的任务
            // 取消请求超时处理调度任务
            asyncTimeoutFuture.cancel(false);
        }

        //设置页码编码
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        //禁用缓存
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache,no-store");
        resp.setDateHeader("Expires", 0);
        resp.setStatus(HttpServletResponse.SC_OK);
        //输出Json流
        sendJsonResult(result);
    }

    /**
     * 发送响应流
     *
     * @param result
     */
    private void sendJsonResult(String result) {
        PrintWriter writer = null;
        try {
            writer = asyncContext.getResponse().getWriter();
            writer.write(JSONUtil.toJsonStr(Dict.create().set("code", 200).set("success", StrUtil.isNotEmpty(result)).set("data", result)));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            asyncContext.complete();
            if (null != writer) {
                writer.close();
            }
        }
    }

    public String getDataId() {
        return dataId;
    }

    public String getMd5Data() {
        return md5Data;
    }
}

