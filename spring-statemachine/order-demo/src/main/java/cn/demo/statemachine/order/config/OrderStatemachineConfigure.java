package cn.demo.statemachine.order.config;

import cn.demo.statemachine.order.model.Order;
import cn.demo.statemachine.order.model.OrderStatus;
import cn.demo.statemachine.order.model.OrderStatusChangeEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableWithStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

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

    @Bean
    public DefaultStateMachinePersister persister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object, Object, Order>() {
            @Override
            public void write(StateMachineContext<Object, Object> context, Order order) throws Exception {
                //此处并没有进行持久化操作
                System.out.println("状态持久化");
            }

            @Override
            public StateMachineContext<Object, Object> read(Order order) throws Exception {
                //此处直接获取order中的状态，其实并没有进行持久化读取操作
                return new DefaultStateMachineContext(order.getStatus(), null, null, null);
            }
        });
    }

}

