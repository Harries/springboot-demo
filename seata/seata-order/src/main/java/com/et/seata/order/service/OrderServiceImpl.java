package com.et.seata.order.service;

import com.alibaba.fastjson.JSONObject;
import com.et.seata.order.dao.OrderDao;
import com.et.seata.order.dto.OrderDO;
import io.seata.core.context.RootContext;
import io.seata.integration.http.DefaultHttpExecutor;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName OrderServiceImpl
 * @Description todo
 * @date 2024/08/08/ 13:53
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderDao orderDao;
    @Override
    @GlobalTransactional // <1>
    public Integer createOrder(Long userId, Long productId, Integer price) throws IOException {
        Integer amount = 1; // 购买数量，暂时设置为 1。

        log.info("[createOrder] 当前 XID: {}", RootContext.getXID());

        // <2> 扣减库存
        this.reduceStock(productId, amount);

        // <3> 扣减余额
        this.reduceBalance(userId, price);

        // <4> 保存订单
        log.info("[createOrder] 保存订单");
        return this.saveOrder(userId,productId,price,amount);


    }
    private Integer saveOrder(Long userId, Long productId, Integer price,Integer amount){
		// <4> 保存订单
		OrderDO order = new OrderDO();
		order.setUserId(userId);
		order.setProductId(productId);
		order.setPayAmount(amount * price);
		orderDao.saveOrder(order);
		log.info("[createOrder] 保存订单: {}", order.getId());
		return order.getId();
	}
    private void reduceStock(Long productId, Integer amount) throws IOException {
        // 参数拼接
        JSONObject params = new JSONObject().fluentPut("productId", String.valueOf(productId))
                .fluentPut("amount", String.valueOf(amount));
        // 执行调用
        HttpResponse response = DefaultHttpExecutor.getInstance().executePost("http://127.0.0.1:8082", "/stock",
                params, HttpResponse.class);
        // 解析结果
        Boolean success = Boolean.valueOf(EntityUtils.toString(response.getEntity()));
        if (!success) {
            throw new RuntimeException("扣除库存失败");
        }
    }

    private void reduceBalance(Long userId, Integer price) throws IOException {
        // 参数拼接
        JSONObject params = new JSONObject().fluentPut("userId", String.valueOf(userId))
                .fluentPut("price", String.valueOf(price));
        // 执行调用
        HttpResponse response = DefaultHttpExecutor.getInstance().executePost("http://127.0.0.1:8083", "/balance",
                params, HttpResponse.class);
        // 解析结果
        Boolean success = Boolean.valueOf(EntityUtils.toString(response.getEntity()));
        if (!success) {
            throw new RuntimeException("扣除余额失败");
        }
    }
}
