package com.et.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.et.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/createOrder")
    public String createOrder(@RequestParam String totalAmount, @RequestParam String subject, Model model) {
        String outTradeNo = "ORDER_" + System.currentTimeMillis(); // 生成订单号
        try {
            String qrCodeUrl = alipayService.createOrder(outTradeNo, totalAmount, subject);
            model.addAttribute("qrCodeUrl", qrCodeUrl);
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("subject", subject);
            model.addAttribute("outTradeNo", outTradeNo);
            return "payment";
        } catch (Exception e) {
            model.addAttribute("error", "创建订单失败");
            return "error";
        }
    }
    @PostMapping("/queryPayment")
    public String queryPayment(String outTradeNo) {
        String TradeStatus=alipayService.queryPayment(outTradeNo);
        if(TradeStatus.equals("TRADE_SUCCESS")||TradeStatus.equals("TRADE_FINISHED")){
            return "success";
        }else{
            return "error";
        }
    }

}