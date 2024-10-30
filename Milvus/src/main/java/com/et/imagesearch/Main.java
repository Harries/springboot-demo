package com.et.imagesearch;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FeatureExtractor extractor = new FeatureExtractor();
        MilvusManager milvusManager = new MilvusManager();
        ImageSearcher searcher = new ImageSearcher();

        // 创建Milvus集合
        milvusManager.createCollection();

        // 假设有一个图像文件列表
        File[] imageFiles = new File("/Users/liuhaihua/ai/ut-zap50k-images-square/Boots/Ankle/Columbia").listFiles();
        if (imageFiles != null) {
            for (int i = 0; i < imageFiles.length; i++) {
                INDArray features = extractor.extractFeatures(imageFiles[i]);
                milvusManager.insertData(i, features);
            }
        }
		milvusManager.flush();
		milvusManager.buildindex();


        // 查询图像
        File queryImage = new File("/Users/liuhaihua/ai/ut-zap50k-images-square/Boots/Ankle/Columbia/7247580.16952.jpg");
        INDArray queryFeatures = extractor.extractFeatures(queryImage);
        searcher.search(queryFeatures);
    }
}