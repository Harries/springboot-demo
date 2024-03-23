package com.et.qrcode.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.et.qrcode.util.QRCodeGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;

/**
 * generate qrcode
 * @author zhouzhaodong
 */
@Controller
public class QRCodeController {

    /**
     * qrcodeImage
     * @param orderNo
     * @return
     */
    @RequestMapping("/original/qrcode/image")
    public String qrcodeImage(String orderNo) {
        String failPath = "D:\\tmp\\" + orderNo + ".png";
        try {
            QRCodeGenerator.generateQRCodeImage(orderNo, 350, 350,  failPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return failPath;
    }

    /**
     * qrcodeBase64
     * @param orderNo
     * @return
     */
    @RequestMapping("/original/qrcode/base64")
    public String qrcodeBase64(String orderNo) {
        String message = "";
        try {
            message = QRCodeGenerator.writeToStream(orderNo, 350, 350);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    @RequestMapping("/hblog/qrcode/image1")
    public String hblogImages1() {
        String message = "";
        try {
            QrConfig config = new QrConfig(300, 300);
            // Set the margin, that is, the margin between the QR code and the background
            config.setMargin(3);
            // Set the foreground color, which is the QR code color (cyan)
            config.setForeColor(Color.CYAN.getRGB());
            // Set background color (gray)
            config.setBackColor(Color.GRAY.getRGB());
            // Generate QR code to file or stream
            QrCodeUtil.generate("http://www.liuhiahua.cn/", config, FileUtil.file("D:\\tmp\\hblog1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @RequestMapping("/hblog/qrcode/image2")
    public String hblogImages2() {
        String message = "";
        try {
            QrCodeUtil.generate(//
                    "http://www.liuhiahua.cn/", //content
                    QrConfig.create().setImg("D:\\tmp\\logo.png"), //logo
                    FileUtil.file("D:\\tmp\\qrcodeWithLogo.jpg")//output file
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

}