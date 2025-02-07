package com.et.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.et.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayService {

    @Autowired
    private AlipayConfig alipayConfig;

    public String createOrder(String outTradeNo, String totalAmount, String subject) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getMerchantPrivateKey(), "json", "UTF-8", alipayConfig.getAlipayPublicKey(), "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\",\"total_amount\":\"" + totalAmount + "\",\"subject\":\"" + subject + "\"}");
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        request.setReturnUrl(alipayConfig.getReturnUrl());
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return response.getQrCode(); // 返回二维码链接
            } else {
                return "创建支付失败: " + response.getMsg();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "异常: " + e.getMessage();
        }
    }
    public String queryPayment(String outTradeNo) {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getMerchantPrivateKey(), "json", "UTF-8", alipayConfig.getAlipayPublicKey(), "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"}");

        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return response.getTradeStatus(); // 返回订单状态
            } else {
                return "查询失败: " + response.getMsg();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "异常: " + e.getMessage();
        }
    }

}