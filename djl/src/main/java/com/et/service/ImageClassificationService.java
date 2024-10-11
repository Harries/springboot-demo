package com.et.service;

import ai.djl.MalformedModelException;
import ai.djl.translate.TranslateException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageClassificationService {
    public String predict(MultipartFile image, String modePath) throws IOException, MalformedModelException, TranslateException;
    public String training(String datasetRoot, String modePath) throws TranslateException, IOException;
}
