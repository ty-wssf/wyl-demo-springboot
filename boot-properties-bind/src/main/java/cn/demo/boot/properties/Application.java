package cn.demo.boot.properties;

import cn.demo.boot.properties.config.BindConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public Application(BindConfig config) {
        System.out.println(config);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
