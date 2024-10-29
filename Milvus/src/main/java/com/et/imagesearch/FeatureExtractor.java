package com.et.imagesearch;

import org.deeplearning4j.zoo.model.ResNet50;
import org.deeplearning4j.zoo.ZooModel;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.datavec.image.loader.NativeImageLoader;

import java.io.File;
import java.io.IOException;

public class FeatureExtractor {
    private ComputationGraph model;

    public FeatureExtractor() throws IOException {
        // 加载预训练模型
        ZooModel<ComputationGraph> zooModel = ResNet50.builder().build();
        model = (ComputationGraph) zooModel.initPretrained();
    }

    public INDArray extractFeatures(File imageFile) throws IOException {
        // 加载和预处理图像
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray image = loader.asMatrix(imageFile);
        ImagePreProcessingScaler scaler = new ImagePreProcessingScaler(0, 1);
        scaler.transform(image);

        // 提取特征
        return model.outputSingle(image);
    }
}