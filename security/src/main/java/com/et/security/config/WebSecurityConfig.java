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
		// The memory user is not configured, but the UserService just created is configured into the AuthenticationManagerBuilder.
        auth.userDetailsService(userService);
    }
 
    @Order
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

				/*.antMatchers("/admin/**").hasRole("ADMIN")//Indicates that the user accessing the URL in the "/admin/**" mode must have the role of ADMIN
				 .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")
				 .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
				 .anyRequest().authenticated()//Indicates that in addition to the URL pattern defined previously, users must access other URLs after authentication (access after logging in) */
 
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