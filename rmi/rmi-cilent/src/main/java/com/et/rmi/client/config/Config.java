package com.et.rmi.client.config;

import om.et.rmi.common.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.logging.Logger;

@Component
public class Config {

    public final Logger log = Logger.getLogger(this.getClass().getName());

    @Bean
    public RmiProxyFactoryBean proxyFactoryBean() {
        String remoteHost = System.getProperty("RMI_SERVER_HOST");
        if(StringUtils.isEmpty(remoteHost)){
            remoteHost="127.0.0.1";
        }
        String rmiHost = String.format("rmi://%s:1199/customerService", remoteHost);
        log.info("RMI Host name is " + rmiHost);
        RmiProxyFactoryBean proxy = new RmiProxyFactoryBean();
        proxy.setServiceInterface(CustomerService.class);
        proxy.setServiceUrl(rmiHost);
        proxy.afterPropertiesSet();
        return proxy;
    }
}
