package demo.hankcs.demo;

import demo.hankcs.hanlp.model.perceptron.CWSTrainer;
import demo.hankcs.hanlp.corpus.MSR;
import demo.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import demo.hankcs.hanlp.model.perceptron.PerceptronTrainer;
import demo.hankcs.hanlp.seg.Segment;
import demo.hankcs.hanlp.seg.common.CWSEvaluator;
import demo.hankcs.hanlp.classification.utilities.io.ConsoleLogger;

import java.io.IOException;

/**
 * 演示如何正确规范地评测中文分词的准确率<br>
 * 1、公平公正。训练模块、分词模块、语料库、评测程序全部开源。
 * 2、禁止使用语料库之外的词典及其等价物（词向量等）。
 * 3、试验结果可复现，可通过其他评分脚本校验。
 */
public class DemoEvaluateCWS
{
    public static void main(String[] args) throws IOException
    {
        ConsoleLogger.logger.start("开始训练...\n");
        PerceptronTrainer trainer = new CWSTrainer();
        PerceptronTrainer.Result result = trainer.train(MSR.TRAIN_PATH, MSR.TRAIN_PATH, MSR.MODEL_PATH,
                                                        0.0, // 压缩比对准确率的影响很小
                                                        50, // 一般50个迭代就差不多收敛了
                                                        8
        );
        ConsoleLogger.logger.finish(" 训练完毕\n");

        Segment segment = new PerceptronLexicalAnalyzer(result.getModel()).enableCustomDictionary(false); // 重要！必须禁用词典
        System.out.println(CWSEvaluator.evaluate(segment, MSR.TEST_PATH, MSR.OUTPUT_PATH, MSR.GOLD_PATH, MSR.TRAIN_WORDS)); // 标准化评测
        // P:96.80 R:96.55 F1:96.68 OOV-R:70.91 IV-R:97.25
        // 受随机数影响，可能在96.60%左右波动
        System.out.printf("上述结果可通过sighan05官方脚本校验：perl %s %s %s %s\n",
            MSR.SIGHAN05_ROOT + "/scripts/score",
            MSR.TRAIN_WORDS,
            MSR.GOLD_PATH,
            MSR.OUTPUT_PATH);
    }
}