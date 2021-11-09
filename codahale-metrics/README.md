# codahale-metrics

## 总结

-  统计某个函数被调用的频率（TPS），使用Meters。
- 统计某个方法的耗时，使用Histograms。--注意时间是以纳秒为单位的
- 既要统计某个方法的TPS又要统计其耗时时，使用Timers。--注意时间是以纳秒为单位的
- counter用于计数
- gauge只用于记录瞬时值

 **counter与gauge：**

- 在某些时候，只能用gauge，比如说这个值是在第三方包提供的，例如guava cache的cache size（而恰好我们将该cache集成在spring cache中，通过注解来使用了），无法用哪个counter来测量
- 在某些时候，只能用counter，比如说一个方法的执行成功与失败次数

**histogram：**

在统计中位数以及95%这样的数据的时候，通常需要把所有的数据拿出来，然后进行运算（在大量的数据下该方法失效，所以采用了水库采集法--**reservoir sampling**，通过维护一个小的、可管理的水库来代表全部统计数据），具体采集法有以下几种：

- Uniform Reservoirs：随机选择具有线性递减概率的储层的值，仅用于长时间的测量。测量统计数据最近是不是发生了变化，不要使用这个（使用下边的指数衰减水库）。
- **Exponentially Decaying Reservoirs**（指数衰减水库）：该水库采集的数据可以代表**大约**最后5分钟的全部数据。该水库也是Times 类metrics使用histogram的默认选择水库。
- Sliding Window Reservoirs：代表过去n次测量的数据
- Sliding Time Window Reservoirs：**严格**的代表过去n秒内的数据（注意与指数衰减库的区别，该方法严格的记录过去的每一秒的数据（而指数衰减其实还是在最后5min进行抽样），所以在高频下可能需要更多内存，而且也是最慢的水库类型）


## 参考资料

- [codahale-metrics基本使用](https://blog.csdn.net/chuifuhuo6864/article/details/100887058)

