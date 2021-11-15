package cn.demo.boot.ehcache.config;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Ehcache配置
 *
 * @author wyl
 * @since 2021-11-15 15:10:01
 */
@Configuration
public class EhcacheConfiguration {
    @Bean
    public CacheManager cacheManager() throws URISyntaxException, FileNotFoundException {
        //*创建springCacheManager接口的具体实现类，参数是javax下面的CacheManager实现类*//*
        return new JCacheCacheManager(jCacheManager());
    }

    @Bean
    public javax.cache.CacheManager jCacheManager() throws URISyntaxException, FileNotFoundException {
        //ehcache实现了javax的CachingProvider接口的具体实现
        EhcacheCachingProvider ehcacheCachingProvider = new EhcacheCachingProvider();
        //根据配置文件获取cachemanager
        URI uri = ResourceUtils.getURL("classpath:ehcache.xml").toURI();
        javax.cache.CacheManager cacheManager = ehcacheCachingProvider.getCacheManager(uri, this.getClass().getClassLoader());
        return cacheManager;
    }
}
