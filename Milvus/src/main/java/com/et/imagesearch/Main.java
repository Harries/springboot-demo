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
        File[] imageFiles = new File("D:\\tmp\\cifar-100-python").listFiles();
        if (imageFiles != null) {
            for (int i = 0; i < imageFiles.length; i++) {
                INDArray features = extractor.extractFeatures(imageFiles[i]);
                milvusManager.insertData(i, features);
            }
        }

        // 查询图像
        File queryImage = new File("D:\\tmp\\sync-files\\111.png");
        INDArray queryFeatures = extractor.extractFeatures(queryImage);
        searcher.search(queryFeatures);
    }
}