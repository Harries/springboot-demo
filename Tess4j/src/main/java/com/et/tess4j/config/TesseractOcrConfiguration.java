package com.et.tess4j.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TesseractOcrConfiguration {

   @Value("${tess4j.datapath}")
   private String dataPath;

   @Bean
   public Tesseract tesseract() {

      Tesseract tesseract = new Tesseract();
      // 设置训练数据文件夹路径
      tesseract.setDatapath(dataPath);
      // 设置为中文简体
      tesseract.setLanguage("chi_sim");
      return tesseract;
   }
}
