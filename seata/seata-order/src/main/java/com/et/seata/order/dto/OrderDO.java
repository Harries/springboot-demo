package com.et.seata.order.dto;

import lombok.Data;

/**
 * 订单实体
 */
@Data
public class OrderDO {
 
    /** 订单编号 **/
    private Integer id;
 
    /** 用户编号 **/
    private Long userId;
 
    /** 产品编号 **/
    private Long productId;
 
    /** 支付金额 **/
    private Integer payAmount;
    

    
}