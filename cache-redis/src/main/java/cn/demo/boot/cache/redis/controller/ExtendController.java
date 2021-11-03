package cn.demo.boot.cache.redis.controller;

import cn.demo.boot.cache.redis.service.ExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wyl
 * @since 2021-11-03 10:42:56
 */
@RestController
public class ExtendController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ExtendService extendService;

    private Object getTtl(String key) {
        return redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ttl(key.getBytes());
            }
        });
    }

    @GetMapping(path = "default")
    public Map<String, Object> key(int id) {
        Map<String, Object> res = new HashMap<>();
        res.put("key0", extendService.key0());
        res.put("key1", extendService.key1(id));
        res.put("key2", extendService.key2(id, id));
        res.put("key3", extendService.key3(res));

        Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<byte[]> sets = connection.keys("k*".getBytes());
            Set<String> ans = new HashSet<>();
            for (byte[] b : sets) {
                ans.add(new String(b));
            }
            return ans;
        });

        res.put("keys", keys);

        Map<String, Object> ttl = new HashMap<>(8);
        for (String key : keys) {
            ttl.put(key, getTtl(key));
        }
        res.put("ttl", ttl);
        return res;
    }

    @GetMapping(path = "self")
    public Map<String, Object> self(int id) {
        Map<String, Object> res = new HashMap<>();
        res.put("self", extendService.selfKey(id));
        Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<byte[]> sets = connection.keys("vv*".getBytes());
            Set<String> ans = new HashSet<>();
            for (byte[] b : sets) {
                ans.add(new String(b));
            }
            return ans;
        });
        res.put("keys", keys);
        return res;
    }

    @GetMapping(path = "ttl")
    public Map ttl(String k) {
        Map<String, Object> res = new HashMap<>();
        res.put("ttl", getTtl("ttl::" + k));
        return res;
    }

}
