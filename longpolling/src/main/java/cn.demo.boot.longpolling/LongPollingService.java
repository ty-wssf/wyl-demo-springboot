package cn.demo.boot.longpolling;

import cn.hutool.core.lang.Singleton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于http的长轮询模型封装
 *
 * @author wyl
 * @since 2021-09-18 13:10:56
 */
public interface LongPollingService {

    /***
     * 数据拉取接口
     * @param dataId
     * @param req
     * @param resp
     */
    void pull(String dataId, String md5Data, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 数据推送接口
     *
     * @param dataId
     * @param data
     */
    void push(String dataId, String data);

    static LongPollingService getInstance() {
        return Singleton.get(LongPollingServiceImpl.class);
    }

}
