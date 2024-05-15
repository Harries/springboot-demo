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

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
 
    AntPathMatcher antPathMatcher = new AntPathMatcher(); // AntPathMatcher is mainly used to implement ant-style URL matching.
 
    @Resource
    MenuMapper menuMapper;

	// Spring Security uses the getAttributes method in the FilterInvocationSecurityMetadataSource interface to determine which roles are required for a request.    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        /*
		 * The parameter of this method is a FilterInvocation. Developers can extract the currently requested URL from the FilterInvocation.
		 * The return value is Collection<ConfigAttribute>, indicating the role required by the current request URL.
         */
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        /*
		 * Obtain all resource information from the database, that is, the menu table in this case and the role corresponding to the menu,
		 * In a real project environment, developers can cache resource information in Redis or other cache databases.
         */
        List<Menu> allMenus = menuMapper.getAllMenus();
		// Traverse the resource information. During the traversal process, obtain the role information required for the currently requested URL and return it.
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
		// If the currently requested URL does not have a corresponding pattern in the resource table, it is assumed that the request can be accessed after logging in, that is, ROLE_LOGIN is returned directly.
        return SecurityConfig.createList("ROLE_LOGIN");
    }
 
    /**
	 * The getAllConfigAttributes method is used to return all defined permission resources. SpringSecurity will verify whether the relevant configuration is correct when starting.
	 * If verification is not required, this method can directly return null.
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
 
    /**
	 * The supports method returns whether the class object supports verification.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}