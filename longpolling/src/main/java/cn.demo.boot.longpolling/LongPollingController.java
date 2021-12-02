package cn.demo.boot.longpolling;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wyl
 * @since 2021-11-29 13:37:52
 */
@RestController
@RequestMapping("/longPolling")
public class LongPollingController {

    /**
     * 客户端通过改接口拉取变更数据
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/pull")
    protected void pull(HttpServletRequest req, HttpServletResponse resp) {
        // 初始化测试数据
        LongPollingDataRepo.save("config", "rmi://10.20.11.216:10230");
        String dataId = req.getParameter("dataId");
        if (StrUtil.isEmpty(dataId)) {
            throw new IllegalArgumentException("请求参数异常,dataId能为空");
        }
        String md5Data = req.getParameter("md5Data");
        LongPollingService.getInstance().pull(dataId, md5Data, req, resp);
    }

    /**
     * 为了在浏览器中演示,我这里先用Get请求,dataId可以为数据分类
     *
     * @param dataId
     * @param data
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam("dataId") String dataId, @RequestParam("data") String data) {
        if (StrUtil.isEmpty(dataId) || StrUtil.isEmpty(data)) {
            throw new IllegalArgumentException("请求参数异常,dataId和data均不能为空");
        }
        LongPollingService.getInstance().push(dataId, data);
        return "推送数据成功";
    }

}
