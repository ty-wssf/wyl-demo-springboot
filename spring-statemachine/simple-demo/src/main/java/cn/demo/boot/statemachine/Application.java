package cn.demo.boot.statemachine;

import cn.demo.boot.statemachine.contants.Events;
import cn.demo.boot.statemachine.contants.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * @author wyl
 * @since 2021-11-03 13:57:38
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------- start -----------------");
        stateMachine.sendEvent(Events.E1);
        stateMachine.sendEvent(Events.E2);
        System.out.println("------------- end -----------------");
    }

}
