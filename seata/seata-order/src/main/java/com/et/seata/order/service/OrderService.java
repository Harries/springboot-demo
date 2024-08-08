package com.et.seata.order.service;

import java.io.IOException;

public interface OrderService {
    public  Integer createOrder(Long userId, Long productId, Integer price) throws IOException;
}
