package com.et.tess4j.service;

import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class OcrService {

    private final Tesseract tesseract;

   /**
    * 识别图片中的文字
    * @param imageFile 图片文件
    * @return 文字信息
    */
    public String recognizeText(MultipartFile imageFile) throws TesseractException, IOException {

        // 转换
        InputStream sbs = new ByteArrayInputStream(imageFile.getBytes());
        BufferedImage bufferedImage = ImageIO.read(sbs);

        // 对图片进行文字识别
        return tesseract.doOCR(bufferedImage);
    }
}
