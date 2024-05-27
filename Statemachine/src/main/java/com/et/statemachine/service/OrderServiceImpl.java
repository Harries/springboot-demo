package com.et.statemachine.service;

import com.et.statemachine.state.Order;
import com.et.statemachine.state.OrderStatusChangeEventEnum;
import com.et.statemachine.state.OrderStatusEnum;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: order service
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StateMachine<OrderStatusEnum, OrderStatusChangeEventEnum> orderStateMachine;

    private long id = 1L;

    private Map<Long, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order create() {
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.WAIT_PAYMENT);
        order.setOrderId(id++);
        orders.put(order.getOrderId(), order);
        System.out.println("order create success:" + order.toString());
        return order;
    }

    @Override
    public Order pay(long id) {
        Order order = orders.get(id);
        System.out.println("try to pay，order no：" + id);
        Message message = MessageBuilder.withPayload(OrderStatusChangeEventEnum.PAYED).
                setHeader("order", order).build();
        if (!sendEvent(message)) {
            System.out.println(" pay fail, error，order no：" + id);
        }
        return orders.get(id);
    }

    @Override
    public Order deliver(long id) {
        Order order = orders.get(id);
        System.out.println(" try to deliver，order no：" + id);
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEventEnum.DELIVERY)
                .setHeader("order", order).build())) {
            System.out.println(" deliver fail，error，order no：" + id);
        }
        return orders.get(id);
    }

    @Override
    public Order receive(long id) {
        Order order = orders.get(id);
        System.out.println(" try to receiver，order no：" + id);
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEventEnum.RECEIVED)
                .setHeader("order", order).build())) {
            System.out.println(" deliver fail，error，order no：" + id);
        }
        return orders.get(id);
    }


    @Override
    public Map<Long, Order> getOrders() {
        return orders;
    }

    /**
     * send transient  event
     * @param message
     * @return
     */
    private synchronized boolean sendEvent(Message<OrderStatusChangeEventEnum> message) {
        boolean result = false;
        try {
            orderStateMachine.start();
            result = orderStateMachine.sendEvent(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(message)) {
                Order order = (Order) message.getHeaders().get("order");
                if (Objects.nonNull(order) && Objects.equals(order.getOrderStatus(), OrderStatusEnum.FINISH)) {
                    orderStateMachine.stop();
                }
            }
        }
        return result;
    }
}