package com.et.seata.product.dto;

import lombok.Data;

@Data
public class ProductReduceStockDTO {
 

    private Long productId;

    private Integer amount;
    

}