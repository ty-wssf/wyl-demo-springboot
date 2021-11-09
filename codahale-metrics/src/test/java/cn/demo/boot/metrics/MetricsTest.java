package cn.demo.boot.metrics;

import com.codahale.metrics.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author wyl
 * @since 2021-11-09 08:49:51
 */
public class MetricsTest {

    /**
     * 作用：统计最近1分钟(m1)，5分钟(m5)，15分钟(m15)，还有全部时间的速率（速率就是平均值）
     * 例如：qps
     * 线程安全：mark()方法中的四个操作都是基于CAS实现，统计线程安全。
     */
    @Test
    public void testMeter() throws InterruptedException {
        //其实就是一个metrics容器，因为该类的一个属性final ConcurrentMap<String, Metric> metrics，在实际使用中做成单例就好
        final MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        //从启动后的1s后开始（所以通常第一个计数都是不准的，从第二个开始会越来越准），每隔一秒从MetricRegistry钟poll一次数据
        reporter.start(1, TimeUnit.SECONDS);
        //将该Meter类型的指定name的metric加入到MetricsRegistry中去
        Meter meterTps = registry.meter(MetricRegistry.name(MetricsTest.class, "request", "tps"));

        System.out.println("执行与业务逻辑");

        while (true) {
            meterTps.mark();//总数以及m1,m5,m15的数据都+1
            Thread.sleep(500);
        }

    }

    /**
     * 作用：返回一个瞬时值（就是一个具体值）
     * 例如：某一时刻的队列size
     * 线程安全：只是做读操作，线程安全
     *
     * @throws InterruptedException
     */
    @Test
    public void testGauge() throws InterruptedException {
        Queue<String> queue = new LinkedList<>();//队列
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        registry.register(MetricRegistry.name(MetricsTest.class, "queue", "size"), new Gauge<Integer>() {
            public Integer getValue() {
                return queue.size();
            }
        });

        while (true) {
            try {
                Thread.sleep(1000);
                queue.add("job - " + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 作用：gauge的AtomicLong实例（Counter 只是用 Gauge 封装了 AtomicLong），可用于加（inc()）减（dec()）
     * 例如：获得队列长度（此处的获取要比使用gauge通过size()方法获取高效很多，后者size()方法的获取大多数是O(n)），方法执行成功失败次数（这个就是gauge无法做的）
     * 作用：AtomicLong基于CAS，线程安全
     */
    @Test
    public void testCounter() throws InterruptedException {
        Queue<String> queue = new LinkedBlockingQueue<>();
        Counter counter;//计算queue的大小
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
        counter = registry.counter(MetricRegistry.name(MetricsTest.class, "queue", "size"));
        int num = 0;
        while (true) {
            if (num < 10) {
                queue.add("job - " + num);
                counter.inc();
            } else if (num > 10 && num < 16) {
                queue.poll();
                counter.dec();
            } else {
                queue.add("job - " + num);
                counter.inc();
            }
            num++;
            Thread.sleep(500);
        }
    }

    /**
     * 作用：计算执行次数count、最小值min，最大值max，平均值mean，方差stddev，中位数median，75百分位, 90百分位, 95百分位, 98百分位, 99百分位, 和 99.9百分位的值
     * 例如：统计某个函数的执行耗时，以上这些值通常会是执行时间，如min是最短执行时间等
     * 线程：update的操作需要获取锁，操作之后释放锁。线程安全。
     *
     * @throws InterruptedException
     */
    @Test
    public void testHistogram() throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        Histogram histogram = new Histogram(new ExponentiallyDecayingReservoir());//95%
        registry.register(MetricRegistry.name(MetricsTest.class, "request", "histogram"), histogram);

        Random random = new Random();
        while (true) {
            Thread.sleep(1000);
            histogram.update(random.nextInt(10000));
        }

    }

    /**
     * 作用：meter和histogram的组合体
     * 例如：统计某个函数的qps和执行耗时。
     * 线程安全：meter和histogram都安全，所以也线程安全
     *
     * @throws InterruptedException
     */
    @Test
    public void testTimer() throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(1, TimeUnit.SECONDS);

        Timer timer = registry.register(MetricRegistry.name(MetricsTest.class, "get-latency"), new Timer(new SlidingWindowReservoir(1000)));
        //Timer timer = registry.timer(MetricRegistry.name(MetricsTest.class, "get-latency"));
        Timer.Context ctx = timer.time();
        while (true) {
            Thread.sleep(1000);
            timer.timeSupplier(Object::new);
        }
        //ctx.stop();
    }

}
