package com.et.spel.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class BaseValueInject {
    @Value("normal")
    private String normal; // 注入普通字符串
 
    @Value("#{systemProperties['os.name']}")
    private String systemPropertiesName; // 注入操作系统属性
 
    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber; //注入表达式结果
 
    @Value("#{beanInject.another}")
    private String fromAnotherBean; // 注入其他Bean属性：注入beanInject对象的属性another，类具体定义见下面
 
    @Value("classpath:config.txt")
    private Resource resourceFile; // 注入文件资源
 
    @Value("http://www.baidu.com")
    private Resource testUrl; // 注入URL资源


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