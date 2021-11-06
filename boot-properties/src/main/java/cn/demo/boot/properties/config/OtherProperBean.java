package cn.demo.boot.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource({"classpath:biz.properties"})
@ConfigurationProperties(prefix = "biz")
public class OtherProperBean {
    private String token;
    private String appKey;
    private Integer appVersion;
    private String source;
    private String uuid;
}
