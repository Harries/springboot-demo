package com.et.statemachine.listener;

import com.et.statemachine.state.Order;
import com.et.statemachine.state.OrderStatusEnum;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: state listener
 */
@Component
@WithStateMachine
@Transactional
public class OrderStatusListener {
    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    public boolean payTransition(Message message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderStatus(OrderStatusEnum.WAIT_DELIVER);
        System.out.println("pay，feedback by statemachine：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public boolean deliverTransition(Message message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE);
        System.out.println("deliver，feedback by statemachine：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public boolean receiveTransition(Message message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setOrderStatus(OrderStatusEnum.FINISH);
        System.out.println("receive，feedback by statemachine：" + message.getHeaders().toString());
        return true;
    }

}