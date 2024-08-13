package com.et.sentinel.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
 
@Configuration
public class SentinelAspectConfig {
 
    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){
        return new SentinelResourceAspect();
    }

}
