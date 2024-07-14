package com.et.groovy.config;

import groovy.lang.Binding;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class GroovyBindingConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    //@Bean("groovyBinding")
    public Binding groovyBinding() {
        Binding groovyBinding = new Binding();

        Map<String, Object> beanMap = applicationContext.getBeansOfType(Object.class);
        //遍历设置所有bean,可以根据需求在循环中对bean做过滤
        for (String beanName : beanMap.keySet()) {
            groovyBinding.setVariable(beanName, beanMap.get(beanName));
        }
        return groovyBinding;
    }

    @Bean("groovyBinding1")
    public Binding groovyBinding1() {
        Map<String, Object> beanMap = applicationContext.getBeansOfType(Object.class);
        return new Binding(beanMap); //如果不需要对bean做过滤，直接用beanMap构造Binding对象即可
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
