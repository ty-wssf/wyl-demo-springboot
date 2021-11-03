package cn.demo.boot.redis.lettuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class Application {

    public Application(RedisTemplate<String, String> redisTemplate) {
        System.out.println(redisTemplate.opsForValue().get("key"));
        redisTemplate.opsForValue().set("key", "value");
        System.out.println(redisTemplate.opsForValue().get("key"));
        redisTemplate.delete("key");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
