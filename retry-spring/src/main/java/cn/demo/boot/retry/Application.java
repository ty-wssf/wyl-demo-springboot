package cn.demo.boot.retry;

import cn.demo.boot.retry.service.PayService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author wyl
 * @since 2021-11-05 14:56:09
 */
@SpringBootApplication
@EnableRetry
public class Application {

    public Application(PayService payService) throws Exception {
        int store = payService.minGoodsnum(-1);
        System.out.println("库存为：" + store);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
