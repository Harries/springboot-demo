package com.et.statemachine.state;

/**
 * @description: order status
 */
public enum OrderStatusEnum {

    WAIT_PAYMENT,

    WAIT_DELIVER,

    WAIT_RECEIVE,

    FINISH;
}
