package cn.demo.statemachine.order.model;

/**
 * @author wyl
 * @since 2021-11-03 15:52:22
 */
public enum OrderStatus {
    // 待支付，待发货，待收货，订单结束
    WAIT_PAYMENT, WAIT_DELIVER, WAIT_RECEIVE, FINISH;
}
