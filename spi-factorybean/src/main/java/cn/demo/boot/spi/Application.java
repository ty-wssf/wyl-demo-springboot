package cn.demo.boot.spi;

import cn.demo.boot.spi.demo.IPrint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public Application(IPrint printProxy) {
        printProxy.print(10, "22");
        printProxy.print(0, "22");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
