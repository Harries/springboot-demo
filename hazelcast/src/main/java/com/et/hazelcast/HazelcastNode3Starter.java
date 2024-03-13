package com.et.hazelcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class HazelcastNode3Starter {
    public static void main(String[] args) {
        SpringApplication.run(HazelcastNode3Starter.class, args);
    }
}