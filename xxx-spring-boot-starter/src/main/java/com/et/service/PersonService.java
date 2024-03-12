package com.et.service;

import com.et.starter.PersonProperties;

public class PersonService {
    private PersonProperties properties;

    public PersonService() {
    }

    public PersonService(PersonProperties properties) {
        this.properties = properties;
    }

    public void sayHello() {
        String message = String.format("hi，my name: %s, today,I'am %s , gender: %s",
                properties.getName(), properties.getAge(), properties.getSex());
        System.out.println(message);
    }
}