package com.et.security.config;

import com.et.security.entity.Menu;
import com.et.security.entity.Role;
import com.et.security.mapper.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
 
    AntPathMatcher antPathMatcher = new AntPathMatcher(); // AntPathMatcher，主要用来实现ant风格的URL匹配。
 
    @Autowired
    MenuMapper menuMapper;
 
    // Spring Security中通过FilterInvocationSecurityMetadataSource接口中的getAttributes方法来确定一个请求需要哪些角色，
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        /*
         * 该方法的参数是一个FilterInvocation，开发者可以从FilterInvocation中提取出当前请求的URL，
         * 返回值是Collection<ConfigAttribute>，表示当前请求URL所需的角色。
         */
        String requestUrl = ((FilterInvocation) object).getRequestUrl();//从参数中提取出当前请求的URL。
        /*
         * 从数据库中获取所有的资源信息，即本案例中的menu表以及menu所对应的role，
         * 在真实项目环境中，开发者可以将资源信息缓存在Redis或者其他缓存数据库中。
         */
        List<Menu> allMenus = menuMapper.getAllMenus();
        // 遍历资源信息，遍历过程中获取当前请求的URL所需要的角色信息并返回。
        for (Menu menu : allMenus) {
            if (antPathMatcher.match(menu.getPattern(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] roleArr = new String[roles.size()];
                for (int i = 0; i < roleArr.length; i++) {
                    roleArr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(roleArr);
            }
        }
        // 如果当前请求的URL在资源表中不存在相应的模式，就假设该请求登录后即可访问，即直接返回ROLE_LOGIN。
        return SecurityConfig.createList("ROLE_LOGIN");
    }
 
    /**
     * getAllConfigAttributes方法用来返回所有定义好的权限资源，SpringSecurity在启动时会校验相关配置是否正确，
     * 如果不需要校验，那么该方法直接返回null即可。
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
 
    /**
     *  supports方法返回类对象是否支持校验。
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}