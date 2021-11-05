package cn.demo.boot.spi.demo;

import cn.demo.boot.spi.enine.SpiFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PrintAutoConfig {

    @Primary
    @Bean
    public IPrint printProxy(ApplicationContext applicationContext) throws Exception {
        return (IPrint) new SpiFactoryBean(applicationContext, IPrint.class).getObject();
    }

}
