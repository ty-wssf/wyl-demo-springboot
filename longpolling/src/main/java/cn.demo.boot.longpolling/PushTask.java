package cn.demo.boot.longpolling;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;

import java.util.Iterator;
import java.util.Queue;

/**
 * push任务
 * 推送到所有订阅当前数据的{@link PullTask}
 *
 * @author wyl
 * @since 2021-09-18 13:12:41
 */
public class PushTask implements Runnable {
    // 数据标识
    private String dataId;
    // 推送数据
    private String data;
    // 拉取数据任务队列
    private Queue<PullTask> pullTasks;

    public PushTask(String dataId, String data,
                    Queue<PullTask> pullTasks) {
        this.dataId = dataId;
        this.data = data;
        this.pullTasks = pullTasks;
    }

    @Override
    public void run() {
        Iterator<PullTask> iterator = pullTasks.iterator();
        while (iterator.hasNext()) {
            PullTask nacosPullTask = iterator.next();
            if (dataId.equals(nacosPullTask.getDataId())) {
                //可根据内容的MD5判断数据是否发生改变
                //移除队列中的任务,确保下次请求时响应的task不是上次请求留在队列中的task
                if (!StrUtil.isAllEmpty(data, nacosPullTask.getMd5Data())) {
                    if (!MD5.create().digestHex(data).equals(nacosPullTask.getMd5Data())) {
                        iterator.remove();
                        //执行数据变更,发送响应
                        nacosPullTask.sendResponse(data);
                        break;
                    }
                }
            }
        }
    }
}
