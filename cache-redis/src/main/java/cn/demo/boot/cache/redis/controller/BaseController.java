package cn.demo.boot.cache.redis.controller;

import cn.demo.boot.cache.redis.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyl
 * @since 2021-11-03 10:41:19
 */
@RestController
public class BaseController {

    @Autowired
    private BasicService helloService;

    @GetMapping(path = {"", "/"})
    public String hello(String name) {
        return helloService.sayHello(name);
    }

    @GetMapping(path = "evict")
    public String evict(String name) {
        return helloService.evict(String.valueOf(name));
    }

    @GetMapping(path = "condition")
    public String t1(int age) {
        return helloService.setByCondition(age);
    }

    @GetMapping(path = "unless")
    public String t2(int age) {
        return helloService.setUnless(age);
    }

    @GetMapping(path = "exception")
    public String exception(int age) {
        try {
            return String.valueOf(helloService.exception(age));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping(path = "inner")
    public String inner(int age) {
        return helloService.innerCall(age);
    }

    @GetMapping(path = "cachePut")
    public String cachePut(int age) {
        return helloService.cachePut(age);
    }

}
