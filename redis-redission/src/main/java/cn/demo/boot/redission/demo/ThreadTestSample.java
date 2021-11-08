package cn.demo.boot.redission.demo;

import lombok.SneakyThrows;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wyl
 * @since 2021-11-08 09:35:37
 */
@Service
public class ThreadTestSample {

    private AtomicInteger count = new AtomicInteger(35);
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 在一个线程持有锁的过程中，不允许其他的线程持有锁
     *
     * @param lockKey
     * @param threadName
     */
    private void threadTest(RedissonClient redissonClient, String lockKey, String threadName, int n) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                if (redissonClient.getLock(lockKey).tryLock(10_000, TimeUnit.MILLISECONDS)) {
                    if (count.get() >= n) {
                        int left = count.addAndGet(-n);
                        System.out.println(threadName + "减库存，剩余: " + left + " 购买: " + n);
                    } else {
                        System.out.println(threadName + "库存不足下单失败，当前库存: " + count.get() + " 购买： " + n);
                    }
                    redissonClient.getLock(lockKey).unlock();
                }
            }
        }).start();
    }

    public void testLock() throws InterruptedException {
        String lockKey = "lock_key";
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            threadTest(redissonClient, lockKey, "t-" + i, random.nextInt(3) + 1);
        }
        Thread.sleep(20 * 1000);
    }

}
