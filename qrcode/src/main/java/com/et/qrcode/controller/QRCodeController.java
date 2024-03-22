package com.et.qrcode.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.et.qrcode.util.QRCodeGenerator;
import org.apache.tomcat.jni.OS;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

/**
 * 生成二维码
 * @author zhouzhaodong
 */
@Controller
public class QRCodeController {

    /**
     * 生成二维码图片并将地址回传给前端
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
     * 生成二维码Base64回传给前端
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


    @RequestMapping("/hutools/qrcode/image1")
    public String hutoolsImages1() {
        String message = "";
        try {
            QrConfig config = new QrConfig(300, 300);
            // 设置边距，既二维码和背景之间的边距
            config.setMargin(3);
            // 设置前景色，既二维码颜色（青色）
            config.setForeColor(Color.CYAN.getRGB());
            // 设置背景色（灰色）
            config.setBackColor(Color.GRAY.getRGB());
            // 生成二维码到文件，也可以到流
            QrCodeUtil.generate("http://hutool.cn/", config, FileUtil.file("D:\\tmp\\hutools1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @RequestMapping("/hutools/qrcode/image2")
    public String hutoolsImages2() {
        String message = "";
        try {
            QrCodeUtil.generate(//
                    "http://hutool.cn/", //二维码内容
                    QrConfig.create().setImg("D:\\tmp\\logo.png"), //附带logo
                    FileUtil.file("D:\\tmp\\qrcodeWithLogo.jpg")//写出到的文件
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

}