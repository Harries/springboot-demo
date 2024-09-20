package com.et.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Component
public class DependencyResolver {

    private final ConfigurableListableBeanFactory beanFactory;

    @Autowired
    public DependencyResolver(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Set<String> getAllDependencies(String beanName) {
        Set<String> dependencies = new HashSet<>();

        // get Bean definite
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

        // reflect
        try {
            Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    dependencies.add(field.getType().getName()); // 添加依赖的类型
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dependencies;
    }
}
