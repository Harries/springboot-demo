package com.et.atomikos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.datasource.test2")
public class DBConfig2 {
    //@Value("${spring.datasource.test2.jdbcurl}")
    //@Value("${jdbcurl}")
    //private String url;
    private String jdbcurl;
    private String username;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;

}