package cn.demo.statemachine.order.config;

import cn.demo.statemachine.order.model.OrderStatus;
import cn.demo.statemachine.order.model.OrderStatusChangeEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableWithStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

/**
 * 订单状态变更状态机配置
 *
 * @author wyl
 * @since 2021-11-03 15:48:39
 */
@Configuration
@EnableWithStateMachine
public class OrderStatemachineConfigure {

    @Bean(name = "orderStateMachine")
    public StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<OrderStatus, OrderStatusChangeEvent> builder = StateMachineBuilder.builder();

        System.out.println("构建订单状态机");

        builder.configureConfiguration()
                .withConfiguration()
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));

        builder.configureTransitions()
                .withExternal()
                .source(OrderStatus.WAIT_PAYMENT)
                .target(OrderStatus.WAIT_DELIVER)
                .event(OrderStatusChangeEvent.PAYED)
                .and()
                .withExternal().
                source(OrderStatus.WAIT_DELIVER)
                .target(OrderStatus.WAIT_RECEIVE)
                .event(OrderStatusChangeEvent.DELIVERY)
                .and()
                .withExternal()
                .source(OrderStatus.WAIT_RECEIVE)
                .target(OrderStatus.FINISH)
                .event(OrderStatusChangeEvent.RECEIVED);

        return builder.build();
    }

}
