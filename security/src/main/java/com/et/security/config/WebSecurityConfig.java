package com.et.security.config;

import com.et.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    UserService userService;
 
    // 角色继承
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_dba > ROLE_admin ROLE_admin > ROLE_user";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
 
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 没有配置内存用户，而是将刚刚创建好的UserService配置到AuthenticationManagerBuilder中。
        auth.userDetailsService(userService);
    }
 
    @Order
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
 
                /*.antMatchers("/admin/**").hasRole("ADMIN")//表示用户访问“/admin/**”模式的URL必须具备ADMIN的角色
                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()//表示除了前面定义的URL模式之外，用户访问其他的URL都必须认证后访问（登录后访问）*/
 
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(cfisms());//动态配置权限
                        object.setAccessDecisionManager(cadm());//角色信息的比对
                        return object;
                    }
                })
 
                .and()
                .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .and()
                .csrf().disable();
    }
 
    @Bean
    CustomFilterInvocationSecurityMetadataSource cfisms() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }
 
    @Bean
    CustomAccessDecisionManager cadm() {
        return new CustomAccessDecisionManager();
    }
}