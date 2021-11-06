package cn.demo.boot.properties.config;

import lombok.Data;

@Data
public class Jwt {

    private String token;

    private Long timestamp;

}
