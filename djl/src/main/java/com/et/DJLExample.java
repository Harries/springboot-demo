package com.et;

import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.basicdataset.cv.classification.Mnist;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.Dataset;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.dataset.Dataset;
import ai.djl.training.dataset.Batch;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.translate.TranslatorUtils;
import ai.djl.translate.TranslatorFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class DJLExample {

    public static void main(String[] args) throws IOException, ModelException, TranslateException {
        // 创建模型
        Model model = Model.newInstance("simple-model");

        // 创建一个简单的神经网络模型
        SequentialBlock block = new SequentialBlock();
        block.add(Linear.builder().setUnits(128).build()); // 隐藏层
        block.add(Linear.builder().setUnits(10).build());  // 输出层（10分类）

        model.setBlock(block);

        // 定义训练配置
        DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.softmaxCrossEntropyLoss())
                .addTrainingListeners(TrainingListener.Defaults.logging());

        // 创建 Trainer
        try (Trainer trainer = model.newTrainer(config)) {
            // 加载 MNIST 数据集
            Mnist mnist = Mnist.builder()
                    .setSampling(32, true)
                    .optUsage(Dataset.Usage.TRAIN)
                    .build();
            mnist.prepare(new ProgressBar());

            // 初始化 Trainer
            trainer.initialize(new Shape(1, 28, 28));  // 输入形状

            // 开始训练
            int numEpochs = 2;
            for (int epoch = 0; epoch < numEpochs; epoch++) {
                for (Batch batch : trainer.iterateDataset(mnist)) {
                    trainer.trainBatch(batch);
                    trainer.step();
                    batch.close();
                }
                System.out.println("Epoch " + epoch + " completed.");
            }

            // 保存模型
            model.save(Paths.get("build/model"), "simple-model");
        }

        // 推理：加载保存的模型并进行推理
        try (Model loadedModel = Model.newInstance("simple-model", Paths.get("build/model"))) {
            Translator<NDArray, NDArray> translator = new MyTranslator();
            try (Predictor<NDArray, NDArray> predictor = loadedModel.newPredictor(translator)) {
                try (NDManager manager = NDManager.newBaseManager()) {
                    NDArray input = manager.randomUniform(0, 1, new Shape(1, 28, 28)); // 随机生成输入
                    NDArray result = predictor.predict(input);
                    System.out.println("Prediction result: " + result);
                }
            }
        }
    }

    // 自定义 Translator
    private static class MyTranslator implements Translator<NDArray, NDArray> {
        @Override
        public NDArray processInput(TranslatorContext ctx, NDArray input) {
            return input;
        }

        @Override
        public NDArray processOutput(TranslatorContext ctx, NDList list) {
            return list.singletonOrThrow();
        }
    }
}
