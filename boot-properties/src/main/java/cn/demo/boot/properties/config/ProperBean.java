package cn.demo.boot.properties.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.proper")
public class ProperBean {
    private String key;
    private Integer id;
    private String value;
}
