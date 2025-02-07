package com.et.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    @Value("${alipay.app_id}")
    private String appId;

    @Value("${alipay.merchant_private_key}")
    private String merchantPrivateKey;

    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;

    @Value("${alipay.notify_url}")
    private String notifyUrl;

    @Value("${alipay.return_url}")
    private String returnUrl;

    @Value("${alipay.gateway_url}")
    private String gatewayUrl;

    // Getter 方法
    public String getAppId() {
        return appId;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }
}