package cn.demo.boot.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author wyl
 * @since 2021-11-03 17:45:29
 */
@SpringBootApplication
public class Application {

    public Application(@Qualifier("localRedisTemplate") RedisTemplate<String, String> localRedisTemplate,
                       @Qualifier("defaultRedisTemplate") RedisTemplate<String, String> defaultRedisTemplate)
            throws InterruptedException {
        // 10s的有效时间
        localRedisTemplate.delete("key");
        localRedisTemplate.opsForValue().set("key", "value", 100, TimeUnit.MILLISECONDS);
        String ans = localRedisTemplate.opsForValue().get("key");
        System.out.println("value".equals(ans));
        TimeUnit.MILLISECONDS.sleep(200);
        ans = localRedisTemplate.opsForValue().get("key");
        System.out.println("value".equals(ans) + " >> false ans should be null! ans=[" + ans + "]");


        defaultRedisTemplate.opsForValue().set("key", "value", 100, TimeUnit.MILLISECONDS);
        ans = defaultRedisTemplate.opsForValue().get("key");
        System.out.println(ans);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
