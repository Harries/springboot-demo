package com.et.google.controller;

import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;


@RestController
@RequestMapping("/oauth")
public class RestAuthController {
	@Value("${google.client-id}")
	private  String clientid;
	@Value("${google.client-secret}")
	private  String clientsecret;
	@Value("${google.redirect-uri}")
	private  String  redirecturi;


    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.login(callback);
    }

    private AuthRequest getAuthRequest() {
        return new AuthGoogleRequest(AuthConfig.builder()
                .clientId(clientid)
                .clientSecret(clientsecret)
                .redirectUri(redirecturi)
                .httpConfig(HttpConfig.builder()
                        .timeout(15000)
                        // host 和 port 请修改为开发环境的参数
                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1087)))
                        .build())
                .build());
    }
}
