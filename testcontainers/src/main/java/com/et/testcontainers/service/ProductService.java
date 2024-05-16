package com.et.testcontainers.service;

import com.et.testcontainers.Repository.ProductRepository;
import com.et.testcontainers.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public Product getProduct(String id) {
        return productRepository.findById(id).orElse(null);
    }


    public void createProduct(Product product) {
        productRepository.save(product);
    }
}