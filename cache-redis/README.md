# spring-cache使用

## 缓存注解介绍

- `@Cacheable`: 缓存存在，则从缓存取；否则执行方法，并将返回结果写入缓存
- `@CacheEvit`: 失效缓存
- `@CachePut`: 更新缓存
- `@Caching`: 都注解组合



## 参考资料

[缓存注解@Cacheable @CacheEvit @CachePut使用姿势介绍](https://spring.hhui.top/spring-blog/2021/06/16/210616-SpringBoot%E7%BC%93%E5%AD%98%E6%B3%A8%E8%A7%A3-Cacheable-CacheEvit-CachePut%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E4%BB%8B%E7%BB%8D/)

[SpringBoot缓存注解@Cacheable之自定义key策略及缓存失效时间指定](https://spring.hhui.top/spring-blog/2021/07/01/210701-SpringBoot%E7%BC%93%E5%AD%98%E6%B3%A8%E8%A7%A3-Cacheable%E4%B9%8B%E8%87%AA%E5%AE%9A%E4%B9%89key%E7%AD%96%E7%95%A5%E5%8F%8A%E7%BC%93%E5%AD%98%E5%A4%B1%E6%95%88%E6%97%B6%E9%97%B4%E6%8C%87%E5%AE%9A/)

