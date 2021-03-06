package cn.demo.statemachine.order.service;

import cn.demo.statemachine.order.model.Order;
import cn.demo.statemachine.order.model.OrderStatus;
import cn.demo.statemachine.order.model.OrderStatusChangeEvent;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wyl
 * @since 2021-11-03 15:56:12
 */
@Service
@WithStateMachine(name = "orderStateMachine")
public class OrderService {

    @Resource(name = "orderStateMachine")
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;
    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

    private volatile long orderId = 1;
    @Getter
    private Map<Long, Order> orders = new ConcurrentHashMap<>();

    public Order creat() {
        Order order = new Order();
        order.setStatus(OrderStatus.WAIT_PAYMENT);
        order.setOrderId(orderId++);
        orders.put(order.getOrderId(), order);
        return order;
    }

    public Order pay(long orderId) {
        Order order = orders.get(orderId);
        System.out.println("to pay: " + order);
        Message<OrderStatusChangeEvent> msg = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED)
                .setHeader("order", order).build();
        if (!send(msg, order)) {
            System.out.println("pay error for: " + order);
        }
        return orders.get(orderId);
    }

    public Order deliver(long orderId) {
        Order order = orders.get(orderId);
        System.out.println("to delivery: " + order);
        Message<OrderStatusChangeEvent> msg = MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY)
                .setHeader("order", order).build();
        if (!send(msg, order)) {
            System.out.println("delivery error for: " + order);
        }
        return orders.get(orderId);
    }

    public Order receiver(long orderId) {
        Order order = orders.get(orderId);
        System.out.println("to receive: " + order);
        Message<OrderStatusChangeEvent> msg = MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED)
                .setHeader("order", order).build();
        if (!send(msg, order)) {
            System.out.println("receive error for: " + order);
        }
        return orders.get(orderId);
    }


    private synchronized boolean send(Message<OrderStatusChangeEvent> msg, Order order) {
        boolean ans = false;
        try {
            orderStateMachine.start();
            Thread.sleep(1000);
            System.out.println("-------- start to send: " + msg);
            // ???????????????????????????
            persister.restore(orderStateMachine, order);
            ans = orderStateMachine.sendEvent(msg);
            persister.persist(orderStateMachine, order);
            System.out.println("-------- send msg over: " + msg + " : " + ans);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @OnTransition
    public @interface StatesOnTransition {

        OrderStatus[] source() default {};

        OrderStatus[] target() default {};

    }

    /**
     * ???????????????
     *
     * @param message
     * @return
     */
    @StatesOnTransition(source = OrderStatus.WAIT_PAYMENT, target = OrderStatus.WAIT_DELIVER)
    public boolean payTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_DELIVER);
        System.out.println("?????? headers=" + message.getHeaders().toString());
        return true;
    }

    @StatesOnTransition(source = OrderStatus.WAIT_DELIVER, target = OrderStatus.WAIT_RECEIVE)
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.WAIT_RECEIVE);
        System.out.println("?????? headers=" + message.getHeaders().toString());
        return true;
    }

    @StatesOnTransition(source = OrderStatus.WAIT_RECEIVE, target = OrderStatus.FINISH)
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStatus(OrderStatus.FINISH);
        System.out.println("?????? headers=" + message.getHeaders().toString());
        return true;
    }

}