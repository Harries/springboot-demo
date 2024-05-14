package com.et.security.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Comparing role information in the AccessDecisionManager class
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
 
    /**
     * Override the decide method, in which it is judged whether the currently logged in user has the role information required for the current request URL. If not, an AccessDeniedException is thrown, otherwise nothing is done.
     *
     * @param auth   Information about the currently logged in user
     * @param object FilterInvocationObject, you can get the current request object
     * @param ca     FilterInvocationSecurityMetadataSource The return value of the getAttributes method in is the role required by the current request URL
     */
    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> ca) {
        Collection<? extends GrantedAuthority> auths = auth.getAuthorities();
        for (ConfigAttribute configAttribute : ca) {
            /*
             * If the required role is ROLE_LOGIN, it means that the currently requested URL can be accessed after the user logs in.
			 * If auth is an instance of UsernamePasswordAuthenticationToken, it means that the current user has logged in and the method ends here, otherwise it enters the normal judgment process.
             */
            if ("ROLE_LOGIN".equals(configAttribute.getAttribute()) && auth instanceof UsernamePasswordAuthenticationToken) {
                return;
            }
            for (GrantedAuthority authority : auths) {
				// If the current user has the role required by the current request, the method ends
                if (configAttribute.getAttribute().equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no permission");
    }
 
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }
 
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}