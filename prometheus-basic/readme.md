# prometheus-basic

普罗米修斯监控

默认的端点: `/actuator/prometheus`

## 统计脚本

qps 统计, 状态码统计

```sh
sum(rate(counter_builder_job_counter1_total{job="prometheus-example"}[10s]))
```


rt 统计

```bash
sum(rate(http_server_requests_seconds_sum{uri="/hello"}[10s])) / sum(rate(http_server_requests_seconds_count{uri="/hello"}[10s]))
```


## 参考资料

- [SpringBoot集成prometheus](https://www.cnblogs.com/xidianzxm/p/11542135.html)
- [基于Micrometer和Prometheus实现度量和监控的方案](https://juejin.cn/post/6847902218910334984#heading-5)