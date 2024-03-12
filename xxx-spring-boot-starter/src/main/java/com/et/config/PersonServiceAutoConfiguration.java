package com.et.config;

import com.et.service.PersonService;
import com.et.starter.PersonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
@EnableConfigurationProperties(PersonProperties.class)
@ConditionalOnClass(PersonService.class)
@ConditionalOnProperty(prefix = "com.person", value = "enabled", matchIfMissing = true)
public class PersonServiceAutoConfiguration {

    @Autowired
    private PersonProperties properties;

    // if spring container do not config bean，auto config PersonService
    @Bean
    @ConditionalOnMissingBean(PersonService.class)  
    public PersonService personService(){
        PersonService personService = new PersonService(properties);
        return personService;
    }
}