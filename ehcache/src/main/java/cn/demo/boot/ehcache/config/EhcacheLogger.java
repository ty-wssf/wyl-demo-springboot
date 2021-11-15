package cn.demo.boot.ehcache.config;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

/**
 * @author wyl
 * @since 2021-11-15 15:10:29
 */
@Component
@Slf4j
public class EhcacheLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        log.debug("Event: " + event.getType() + " Key: " + event.getKey() + " old value: " + event.getOldValue() + " new value: " + event.getNewValue());
    }
}
