package com.et.service;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.OAuth2Error;


import java.util.Map;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OidcUserService oidcUserService = new OidcUserService();
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        if (isOidcProvider(userRequest.getClientRegistration())) {
            // 尝试获取 OIDC ID Token
            OidcIdToken idToken = extractOidcIdToken(userRequest);
            OidcUserRequest oidcUserRequest = new OidcUserRequest(
                    userRequest.getClientRegistration(),
                    userRequest.getAccessToken(),
                    idToken);
            return oidcUserService.loadUser(oidcUserRequest);
        } else {
            return defaultOAuth2UserService.loadUser(userRequest);
        }
    }

    private boolean isOidcProvider(ClientRegistration clientRegistration) {
        return clientRegistration.getProviderDetails()
                .getConfigurationMetadata()
                .containsKey("userinfo_endpoint");
    }

    private OidcIdToken extractOidcIdToken(OAuth2UserRequest userRequest) {
        // 从 userRequest 中获取 OIDC ID Token，这里假设它已经包含在 access token response 的附加参数中
        // 如果不存在，则需要处理这个情况，可能是返回 null 或抛出异常
        Map<String, Object> additionalParameters = userRequest.getAdditionalParameters();
        Object idTokenObj = additionalParameters.get("id_token");

        if (idTokenObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> idTokenClaims = (Map<String, Object>) idTokenObj;
            return new OidcIdToken(userRequest.getAccessToken().getTokenValue(),
                    userRequest.getAccessToken().getIssuedAt(),
                    userRequest.getAccessToken().getExpiresAt(),
                    idTokenClaims);
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("invalid_id_token"), "Invalid or missing ID token");
    }
}
