package com.et.spel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class BaseValueInject {
    @Value("normal")
    private String normal; // Inject ordinary string
 
    @Value("#{systemProperties['os.name']}")
    private String systemPropertiesName; //Inject operating system properties
 
    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber; //Inject expression result
 
    @Value("#{beanInject.another}")
    private String fromAnotherBean; // Inject other Bean attributes: Inject the attribute another of the beanInject object. See the specific definition of the class below.
 
    @Value("classpath:config.txt")
    private Resource resourceFile; // Inject file resources
 
    @Value("http://www.baidu.com")
    private Resource testUrl; // Inject URL resources


    @Override
    public String toString() {
        return "BaseValueInject{" +
                "normal='" + normal + '\'' +
                ", systemPropertiesName='" + systemPropertiesName + '\'' +
                ", randomNumber=" + randomNumber +
                ", fromAnotherBean='" + fromAnotherBean + '\'' +
                ", resourceFile=" + resourceFile +
                ", testUrl=" + testUrl +
                '}';
    }
}