package cn.demo.boot.spi.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogPrint implements IPrint {

    @Override
    public boolean verify(Integer condition) {
        return condition > 0;
    }

    @Override
    public void print(Integer level, String msg) {
        log.info("log print: {}", msg);
    }

}
