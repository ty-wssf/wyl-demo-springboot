package cn.demo.boot.properties.config;

import cn.demo.boot.properties.config.factory.YamlSourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@Data
@Configuration
@PropertySource(value = {"classpath:biz2.yml"}, factory = YamlSourceFactory.class)
@ConfigurationProperties(prefix = "biz2.yml")
public class YmlProperties {

    private Integer type;

    private String name;

    private List<Map<String, String>> ary;
}
