package com.et.statemachine.state;

/**
 * @description: 订单状态
 */
public enum OrderStatusEnum {

    WAIT_PAYMENT,

    WAIT_DELIVER,

    WAIT_RECEIVE,

    FINISH;
}
