package cn.demo.boot.ehcache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Ehcache缓存操作工具类
 *
 * @author wyl
 * @since 2021-11-15 15:10:51
 */
@Component
@Slf4j
public class EhcacheService implements ApplicationContextAware {

    private static CacheManager cacheManager = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cacheManager = applicationContext.getBean(CacheManager.class);
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * 对指定名称的缓存，设置对应的索引与值
     *
     * @param cacheName 缓存名称
     * @param key       缓存键索引
     * @param value     缓存索引映射的值
     * @return 当操作成功时返回true, 否则返回false
     */
    public static boolean put(String cacheName, String key, Object value) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
            return true;
        }
        return false;
    }

    public static boolean putBySync(String cacheName, String key, Object value) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            synchronized (cache) {
                cache.put(key, value);
                return true;
            }
        }
        return false;
    }

    /**
     * 对指定名称的缓存，incr 1
     *
     * @param cacheName 缓存名称
     * @param key       缓存键索引
     * @return
     */
    public static Object addAndGet(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            synchronized (cache) {
                int c = Objects.isNull(cache.get(key)) ? 0 : (int) cache.get(key).get();
                int i = (int) c + 1;
                cache.put(key, i);
                return cache.get(key).get();
            }

        }
        return null;
    }

    /**
     * @param cacheName
     * @param key
     * @param incrNum   增加数
     * @return
     */
    public static Object addAndGet(String cacheName, String key, long incrNum) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            synchronized (cache) {
                long c = Objects.isNull(cache.get(key)) ? 0 : (long) cache.get(key).get();
                long i = c + incrNum;
                cache.put(key, i);
                return cache.get(key).get();
            }

        }
        return null;
    }

    /**
     * 对指定名称的缓存，create
     *
     * @param cacheName 缓存名称
     * @param key       缓存键索引
     * @return
     */
    public static Object initCache(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            synchronized (cache) {
                if (Objects.isNull(cache.get(key))) {
                    cache.put(key, 0);
                }
                return cache.get(key).get();
            }

        }
        return null;
    }

    public static <T> T get(String cacheName, String key, Class<T> classType) {
        Cache cache = getCache(cacheName);
        T value = null;
        if (cache != null) {
            try {
                value = cache.get(key, classType);
                if (value == null) {
//                    log.info("当前缓存cacheName:{} key:{}的映射值为空", cacheName, key);
                }
            } catch (IllegalStateException e) {
                log.error("当前缓存cacheName:{} key:{}的映射值的类型并非{}类型", cacheName, key, classType.getName());
            }
        }
        return value;
    }

    /**
     * 删除缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static boolean remove(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            return true;
        }
        return false;
    }

    public static Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.error("缓存{}并不存在", cacheName);
            return null;
        }
        return cache;
    }

}
