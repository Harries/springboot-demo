package com.et.flink.job;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName DataStreamAPI
 * @Description todo
 * @date 2024年02月29日 17:01
 */

public class DataStreamAPI {
    public static void main(String[] args) throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从项目根目录下的data目录下的word.txt文件中读取数据
        DataStreamSource<String> source = env.readTextFile("D:\\IdeaProjects\\ETFramework\\flink\\src\\main\\resources\\word.txt");

        // 处理数据: 切分、转换
        SingleOutputStreamOperator<Tuple2<String, Integer>> wordAndOneDS = source
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        // 按空格切分
                        String[] words = value.split(" ");
                        for (String word : words) {
                            // 转换成二元组 （word，1）
                            Tuple2<String, Integer> wordsAndOne = Tuple2.of(word, 1);
                            // 通过采集器向下游发送数据
                            out.collect(wordsAndOne);
                        }
                    }
                });

        // 处理数据:分组
        KeyedStream<Tuple2<String, Integer>, String> wordAndOneKS = wordAndOneDS.keyBy(
                new KeySelector<Tuple2<String, Integer>, String>() {
                    @Override
                    public String getKey(Tuple2<String, Integer> value) throws Exception {
                        return value.f0;
                    }
                }
        );
        // 处理数据:聚合
        SingleOutputStreamOperator<Tuple2<String, Integer>> sumDS = wordAndOneKS.sum(1);

        // 输出数据
        sumDS.print();

        // 执行
        env.execute();
    }

}
