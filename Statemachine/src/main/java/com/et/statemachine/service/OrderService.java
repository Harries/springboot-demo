package com.et.statemachine.service;

import com.et.statemachine.state.Order;

import java.util.Map;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName OrderService
 * @Description todo
 * @date 2024年05月27日 15:15
 */

public interface OrderService {

    Order create();

    Order pay(long id);

    Order deliver(long id);

    Order receive(long id);

    Map<Long, Order> getOrders();
}
