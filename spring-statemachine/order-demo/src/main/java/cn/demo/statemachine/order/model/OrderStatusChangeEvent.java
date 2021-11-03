package cn.demo.statemachine.order.model;

/**
 * @author wyl
 * @since 2021-11-03 15:52:49
 */
public enum OrderStatusChangeEvent {
    // 支付，发货，确认收货
    PAYED, DELIVERY, RECEIVED;
}
