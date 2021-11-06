package cn.demo.boot.properties.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({BindConfig.class})
@Configuration
public class AutoConfiguration {
}
