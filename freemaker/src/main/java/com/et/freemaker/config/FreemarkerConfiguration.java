package com.et.freemaker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import freemarker.template.TemplateModelException;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfiguration {

    //Configuration
    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void configuration() throws TemplateModelException {
        // add globe variable
        this.configuration.setSharedVariable("app", "Spring Boot");
    }
}