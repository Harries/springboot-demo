package com.et.spel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanInject {
    @Value("another sss")
    private String another;
 
    public String getAnother() {
        return another;
    }
 
    public void setAnother(String another) {
        this.another = another;
    }
}