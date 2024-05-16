package com.et.testcontainers.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("product")
@AllArgsConstructor
@Data
public class Product implements Serializable {
    private String id;
    private String name;
    private double price;


}