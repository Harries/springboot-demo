package com.et.clerk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login", "/static/**","/api/**").permitAll() // 允许访问登录页面和静态资源
                .anyRequest().authenticated() // 其他请求需要认证
            .and()
            .formLogin()
                .loginPage("/login") // 自定义登录页面
                .permitAll();
    }
}