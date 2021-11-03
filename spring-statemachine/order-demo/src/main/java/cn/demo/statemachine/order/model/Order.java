package cn.demo.statemachine.order.model;

import lombok.Data;

/**
 * @author wyl
 * @since 2021-11-03 15:49:43
 */
@Data
public class Order {
    private Long orderId;
    private OrderStatus status;
}
