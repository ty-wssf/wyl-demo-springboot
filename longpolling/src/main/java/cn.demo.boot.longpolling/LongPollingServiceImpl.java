package cn.demo.boot.longpolling;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 基于http的长轮询实现
 * 1. 调用{@link this#pull}将请求放到队列中
 * 2. 如果在25秒内数据变更，则返回该请求数据，否则按超时返回处理
 * 3. 客户端请求时设置超时时间为30s，防止客户端和服务端的请求出现问题
 *
 * @author wyl
 * @since 2021-09-18 13:16:58
 */
@Slf4j
public class LongPollingServiceImpl implements LongPollingService {
    // 轮询服务调度服务
    final ScheduledExecutorService scheduler;
    // 拉取请求任务实例
    public final Queue<PullTask> pullTasks;

    public LongPollingServiceImpl() {
        scheduler = new ScheduledThreadPoolExecutor(1, r -> {
            Thread t = new Thread(r);
            t.setName("LongPollingTask");
            t.setDaemon(true);
            return t;
        });
        pullTasks = new ConcurrentLinkedQueue<>();
        scheduler.scheduleAtFixedRate(() -> log.debug("线程存活状态:" + DateUtil.now()), 0L, 60, TimeUnit.SECONDS);
    }

    @Override
    public void pull(String dataId, String md5Data, HttpServletRequest req, HttpServletResponse resp) {
        // 一定要由当前HTTP线程调用，如果放在task线程容器会立即发送响应
        final AsyncContext asyncContext = req.startAsync();
        scheduler.execute(new PullTask(pullTasks, scheduler, asyncContext, dataId, md5Data, req, resp));
    }

    @Override
    public void push(String dataId, String data) {
        // 立即执行推送任务
        scheduler.schedule(new PushTask(dataId, data, pullTasks), 0L, TimeUnit.MILLISECONDS);
    }

}
