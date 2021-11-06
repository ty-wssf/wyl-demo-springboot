package cn.demo.boot.properties.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class JwtConverter implements Converter<String, Jwt> {
    @Override
    public Jwt convert(String source) {
        return JSONObject.parseObject(source, Jwt.class);
    }
}
