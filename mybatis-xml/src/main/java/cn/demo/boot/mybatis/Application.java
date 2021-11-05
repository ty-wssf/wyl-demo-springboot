package cn.demo.boot.mybatis;

import cn.demo.boot.mybatis.mapper.SysUserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wyl
 * @since 2021-11-05 17:01:59
 */
@MapperScan("cn.demo.boot.mybatis.mapper")
@SpringBootApplication
public class Application {

    public Application(SysUserMapper sysUserMapper) {
        sysUserMapper.selectByPrimaryKey(1L);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
