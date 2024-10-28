package com.et.imagesearch;

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
        File[] imageFiles = new File("path/to/cifar-10").listFiles();
        if (imageFiles != null) {
            for (int i = 0; i < imageFiles.length; i++) {
                INDArray features = extractor.extractFeatures(imageFiles[i]);
                milvusManager.insertData(i, features);
            }
        }

        // 查询图像
        File queryImage = new File("path/to/query/image.jpg");
        INDArray queryFeatures = extractor.extractFeatures(queryImage);
        searcher.search(queryFeatures);
    }
}