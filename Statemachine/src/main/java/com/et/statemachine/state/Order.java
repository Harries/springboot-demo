package com.et.statemachine.state;

import lombok.Data;

/**
 * @description: mock orders
 */
@Data
public class Order {
    private Long orderId;
    private OrderStatusEnum orderStatus;
}
