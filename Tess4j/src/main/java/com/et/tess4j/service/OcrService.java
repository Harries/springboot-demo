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


    public String recognizeText(MultipartFile imageFile) throws TesseractException, IOException {

        InputStream sbs = new ByteArrayInputStream(imageFile.getBytes());
        BufferedImage bufferedImage = ImageIO.read(sbs);

        return tesseract.doOCR(bufferedImage);
    }
}
