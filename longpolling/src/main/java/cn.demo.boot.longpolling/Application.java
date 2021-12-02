package cn.demo.boot.longpolling;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyl
 * @since 2021-11-29 13:35:52
 */
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        while (true) {
            try {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("dataId", "config");
                paramMap.put("md5Data", "058882945467a5300e66fc0b69e2ad38");
                String result = HttpUtil.post(StrUtil.format("http://{}:{}/longPolling/pull",
                        "10.20.11.216",
                        "9080"), paramMap, 10_000);
                log.info("获取到配置：{}", result);
            } catch (Exception e) {
                log.error("获取配置异常");
            }

        }
    }

}
