package com.et.flink.job;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName SocketJob
 * @Description todo
 * @date 2024年02月29日 17:06
 */

public class SocketJob {
    public static void main(String[] args) throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 指定并行度,默认电脑线程数
        env.setParallelism(3);
        // 读取数据socket文本流 指定监听 IP 端口 只有在接收到数据才会执行任务
        DataStreamSource<String> socketDS = env.socketTextStream("172.24.4.193", 8888);

        // 处理数据: 切换、转换、分组、聚合 得到统计结果
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = socketDS
                .flatMap(
                        (String value, Collector<Tuple2<String, Integer>> out) -> {
                            String[] words = value.split(" ");
                            for (String word : words) {
                                out.collect(Tuple2.of(word, 1));
                            }
                        }
                )
                .setParallelism(2)
                // // 显式地提供类型信息:对于flatMap传入Lambda表达式，系统只能推断出返回的是Tuple2类型，而无法得到Tuple2<String, Long>。只有显式设置系统当前返回类型，才能正确解析出完整数据
                .returns(new TypeHint<Tuple2<String, Integer>>() {
                })
//                .returns(Types.TUPLE(Types.STRING,Types.INT))
                .keyBy(value -> value.f0)
                .sum(1);


        // 输出
        sum.print();

        // 执行
        env.execute();
    }

}
