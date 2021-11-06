package cn.demo.boot.properties;

import cn.demo.boot.properties.config.OtherProperBean;
import cn.demo.boot.properties.config.ProperBean;
import cn.demo.boot.properties.config.YmlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class Application {

    @Autowired
    private ProperBean properBean;
    @Autowired
    private OtherProperBean otherProperBean;
    @Autowired
    private YmlProperties ymlProperties;

    @PostConstruct
    public void test() {
        log.info("{}", properBean);
        log.info("{}", otherProperBean);
        log.info("{}", ymlProperties);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
