package cn.demo.statemachine.order;

import cn.demo.statemachine.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CyclicBarrier;

/**
 * @author wyl
 * @since 2021-11-03 13:57:38
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private OrderService orderService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    final CyclicBarrier cb = new CyclicBarrier(3);

    @Override
    public void run(String... args) throws Exception {
        orderService.creat();
        orderService.pay(1L);
        orderService.deliver(1L);
        orderService.receiver(1L);
        System.out.println(orderService.getOrders());

        // 测试状态机是否存在多线程问题
        /*orderService.creat();
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    cb.await();
                    orderService.pay(1L);
                }
            }).start();
        }*/

    }

}
