package com.et.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.et.graphql.queryresolvers")
public class BookAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookAPIApplication.class, args);
    }

}
