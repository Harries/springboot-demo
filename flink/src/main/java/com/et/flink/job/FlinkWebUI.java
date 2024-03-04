package com.et.flink.job;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName FlinkWebUI
 * @Description todo
 * @date 2024年02月29日 17:15
 */

public class FlinkWebUI {
    /**
     * 并行度优先级：算子 > 全局env > 提交指定 > 配置文件
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 本地模式
        Configuration conf = new Configuration();
        // 指定端口
        conf.setString(RestOptions.BIND_PORT, "7777");
        //  创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        // 全局指定并行度，默认是电脑的线程数
        env.setParallelism(2);

        // 读取socket文本流
        DataStreamSource<String> socketDS = env.socketTextStream("172.24.4.193", 8888);

        //  处理数据: 切割、转换、分组、聚合 得到统计结果
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = socketDS
                .flatMap(
                        (String value, Collector<Tuple2<String, Integer>> out) -> {
                            String[] words = value.split(" ");
                            for (String word : words) {
                                out.collect(Tuple2.of(word, 1));
                            }
                        }
                )
                // 局部设置算子并行度
                .setParallelism(3)
                .returns(Types.TUPLE(Types.STRING, Types.INT))
                .keyBy(value -> value.f0)
                .sum(1)
                // 局部设置算子并行度
                .setParallelism(4);

        //  输出
        sum.print();

        //  执行
        env.execute();
    }


}
