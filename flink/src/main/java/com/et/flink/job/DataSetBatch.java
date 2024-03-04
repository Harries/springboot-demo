package com.et.flink.job;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class DataSetBatch {

    public static void main(String[] args) throws Exception {
        // 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 获取文件路径
        String path = DataSetBatch.class.getClassLoader().getResource("word.txt").getPath();
        // 从文件中读取数据
        DataSource<String> lineDS = env.readTextFile(path);

        // 切分、转换，例如： （word，1）
        FlatMapOperator<String, Tuple2<String, Integer>> wordAndOne = lineDS.flatMap(new MyFlatMapper());

        // 按word分组 按照第一个位置的word分组
        UnsortedGrouping<Tuple2<String, Integer>> wordAndOneGroupby = wordAndOne.groupBy(0);

        // 分组内聚合统计 将第二个位置上的数据求和
        AggregateOperator<Tuple2<String, Integer>> sum = wordAndOneGroupby.sum(1);

        // 输出
        sum.print();
    }


    /**
     * 自定义MyFlatMapper类，实现FlatMapFunction接口
     * 输出: String 元组Tuple2<String, Integer>>  Tuple2是flink提供的元组类型
     */
    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        //value是输入，out就是输出的数据
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
            // 按空格切分单词
            String[] words = value.split(" ");
            // 遍历所有word，包成二元组输出 将单词转换为 （word，1）
            for (String word : words) {
                Tuple2<String, Integer> wordTuple2 = Tuple2.of(word, 1);
                //  使用Collector向下游发送数据
                out.collect(wordTuple2);
            }
        }
    }
}

