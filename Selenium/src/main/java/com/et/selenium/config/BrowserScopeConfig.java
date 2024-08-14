package com.et.selenium.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// this is how spring boot reads custom scope
@Configuration
public class BrowserScopeConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor(){
        return new BrowserScopePostProcessor();
    }
}
