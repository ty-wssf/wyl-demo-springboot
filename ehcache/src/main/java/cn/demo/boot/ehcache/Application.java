package cn.demo.boot.ehcache;

import cn.demo.boot.ehcache.config.EhcacheService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wyl
 * @since 2021-11-15 15:15:25
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        TimeInterval timer = DateUtil.timer();
        EhcacheService.remove("image_count_cache", "image_count_cache");
        for (int i = 0; i < 10000; i++) {
            EhcacheService.addAndGet("image_count_cache", "image_count_cache");
            System.out.println(EhcacheService.get("image_count_cache", "image_count_cache", Integer.class));
        }
        System.out.println(timer.interval());//花费毫秒数

    }

}
