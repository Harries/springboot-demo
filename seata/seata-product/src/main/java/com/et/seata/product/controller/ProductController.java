package com.et.seata.product.controller;

import com.et.seata.product.dto.ProductReduceStockDTO;
import com.et.seata.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProductController {
	@Autowired
	ProductService productService;
	@PostMapping("/stock")
	public Boolean reduceStock(@RequestBody ProductReduceStockDTO productReduceStockDTO) {
		log.info("[reduceStock] 收到减少库存请求, 商品:{}, 价格:{}", productReduceStockDTO.getProductId(),
				productReduceStockDTO.getAmount());
		try {
			productService.reduceStock(productReduceStockDTO.getProductId(), productReduceStockDTO.getAmount());
			// 正常扣除库存，返回 true
			return true;
		} catch (Exception e) {
			// 失败扣除库存，返回 false
			return false;
		}
	}
}