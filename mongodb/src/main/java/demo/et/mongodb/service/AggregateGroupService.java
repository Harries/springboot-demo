package demo.et.mongodb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 聚合表达式 $group
 *
 * @author mydlq
 */
@Slf4j
@Service
public class AggregateGroupService {

    /**
     * 设置集合名称
     */
    private static final String COLLECTION_NAME = "users";

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 使用管道操作符 $group 结合 $count 方法进行聚合统计
     *
     * @return 聚合结果
     */
    public Object aggregationGroupCount() {
        // 使用管道操作符 $group 进行分组，然后统计各个组的文档数量
        AggregationOperation group = Aggregation.group("age").count().as("numCount");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $max 进行聚合统计
     *
     * @return 聚合结果
     */
    public Object aggregationGroupMax() {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段最大值
        AggregationOperation group = Aggregation.group("sex").max("salary").as("salaryMax");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $min 进行聚合统计
     *
     * @return 聚合结果
     */
    public Object aggregationGroupMin() {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段最小值
        AggregationOperation group = Aggregation.group("sex").min("salary").as("salaryMin");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $sum 进行聚合统计
     *
     * @return 聚合结果
     */
    public Object aggregationGroupSum() {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段值合计
        AggregationOperation group = Aggregation.group("sex").sum("salary").as("salarySum");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $avg 进行聚合统计
     *
     * @return 聚合结果
     */
    public Object aggregationGroupAvg() {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段值平均值
        AggregationOperation group = Aggregation.group("sex").avg("salary").as("salaryAvg");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $first 获取每个组的包含某字段的文档的第一条数据
     *
     * @return 聚合结果
     */
    public Object aggregationGroupFirst() {
        // 先对数据进行排序，然后使用管道操作符 $group 进行分组，最后统计各个组文档某字段值第一个值
        AggregationOperation sort = Aggregation.sort(Sort.by("salary").ascending());
        AggregationOperation group = Aggregation.group("sex").first("salary").as("salaryFirst");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(sort, group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $last 获取每个组的包含某字段的文档的最后一条数据
     *
     * @return 聚合结果
     */
    public Object aggregationGroupLast() {
        // 先对数据进行排序，然后使用管道操作符 $group 进行分组，最后统计各个组文档某字段值第最后一个值
        AggregationOperation sort = Aggregation.sort(Sort.by("salary").ascending());
        AggregationOperation group = Aggregation.group("sex").last("salary").as("salaryLast");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(sort, group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $push 获取某字段列表
     *
     * @return 聚合结果
     */
    public Object aggregationGroupPush() {
        // 先对数据进行排序，然后使用管道操作符 $group 进行分组，然后以数组形式列出某字段的全部值
        AggregationOperation push = Aggregation.group("sex").push("salary").as("salaryFirst");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(push);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : results.getMappedResults()) {
            log.info("{}", result);
        }
        return results.getMappedResults();
    }

}
