package com.et.poster.service;


import com.quaint.poster.core.impl.PosterDefaultImpl;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Random;

/**
 * 绘制海报本地测试
 * @author quaint
 * @date 21 February 2020
 * @since 1.0
 */
@Slf4j
public class PosterUtil {
	public static String ossDomain="http://qdliuhaihua.oss-cn-qingdao-internal.aliyuncs.com/";

    public static void main(String[] args) throws Exception{
		//String in1pathString = PosterUtil.class.getResource("/static/css/fonts/simkai.ttf").getFile();
		//System.out.printf(in1pathString);
		test();

    }

	public   static  byte[] test() throws IOException, IllegalAccessException, FontFormatException {
		// 测试注解, 这里读取图片如果不成功，请查看target 或者 out 目录下是否加载了资源。 如需使用，请引入spring core依赖
		BufferedImage background = ImageIO.read(new URL("http://image.liuhaihua.cn/bg.jpg"));
		File file2=  new File("/Users/liuhaihua/Downloads/2.jpg");
		BufferedImage mainImage = ImageIO.read(file2);
		BufferedImage siteSlogon = ImageIO.read(new URL("http://image.liuhaihua.cn/site.jpg"));
		BufferedImage xx = ImageIO.read(new URL("http://image.liuhaihua.cn/xx.jpg"));
		File file5=  new File("/Users/liuhaihua/IdeaProjects/springboot-demo/poster/src/main/resources/image/wx_300px.png");


		BufferedImage qrcode = ImageIO.read(file5);
		SamplePoster poster = SamplePoster.builder()
				.backgroundImage(background)
				.postQrcode(qrcode)
				.xuxian(xx)
				.siteSlogon(siteSlogon)
				.postTitle("Java5分钟制作海报")
				.postDate("2022年11月14日 pm1:23 作者：Harries")
				.posttitleDesc("需求背景\u200B 我们经常在多终端应用开发中会遇到这样的需求：用户在浏览商品时觉得不错，希望分享给朋友。此时终端（安卓、苹果、H5等）生成一张精美的商品海报，通过微信或者其他途径分享给他人。也可能会遇到需求：制作个人名片打印出来或者分享给他人。效果大概是这样的： 也可能是这样 （上面的图都是我...")
				.mainImage(mainImage)
				.build();

		PosterDefaultImpl<SamplePoster> impl = new PosterDefaultImpl<>();
		BufferedImage test = impl.annotationDrawPoster(poster).draw(null);
		//ImageIO.write(test,"png",new FileOutputStream("/Users/liuhaihua/annTest.png"));
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ImageIO.write(test, "png", stream);
		return stream.toByteArray();
	}


}