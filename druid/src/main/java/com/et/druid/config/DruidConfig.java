package com.et.druid.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        return new DruidDataSource();
    }
    // 配置Druid监控
    // 配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams=new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","admin");
        initParams.put("allow","");  // 默认就是允许所有访问
       // initParams.put("deny","127.0.0.1"); // 拒绝哪个网址访问，优先级大于allow
        bean.setInitParameters(initParams);
        return bean;
    }
    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        // 配置不拦截哪些请求
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        // 配置拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return  bean;
    }
}