package cn.demo.boot.spi.demo;

import org.springframework.stereotype.Component;

@Component
public class ConsolePrint implements IPrint {

    @Override
    public boolean verify(Integer condition) {
        return condition <= 0;
    }

    @Override
    public void print(Integer level, String msg) {
        System.out.println("console print: " + msg);
    }

}
