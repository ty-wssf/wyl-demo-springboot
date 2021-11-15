package cn.demo.boot.h2database;

import cn.demo.boot.h2database.entity.TestCountEntity;
import cn.demo.boot.h2database.entity.TestCountRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wyl
 * @since 2021-11-15 14:44:26
 */
@SpringBootApplication
public class Application implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private TestCountRepository countRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        TestCountEntity testCountEntity = new TestCountEntity();
        testCountEntity.setId(1);
        testCountEntity.setCount(10L);
        countRepository.save(testCountEntity);
    }

}
