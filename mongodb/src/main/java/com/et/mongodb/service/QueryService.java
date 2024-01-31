package com.et.mongodb.service;

import com.et.mongodb.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class QueryService {

    /**
     * 设置集合名称
     */
    private static final String COLLECTION_NAME = "users";

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 查询集合中的【全部】文档数据
     *
     * @return 全部文档列表
     */
    public Object findAll() {
        // 执行查询集合中全部文档信息
        List<User> documentList = mongoTemplate.findAll(User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【文档ID】查询集合中文档数据
     *
     * @return 文档信息
     */
    public Object findById() {
        // 设置查询的文档 ID
        String id = "1";
        // 根据文档ID查询集合中文档数据，并转换为对应 Java 对象
        User user = mongoTemplate.findById(id, User.class, COLLECTION_NAME);
        // 输出结果
        log.info("用户信息：{}", user);
        return user;
    }

    /**
     * 根据【条件】查询集合中【符合条件】的文档，只取【第一条】数据
     *
     * @return 符合条件的第一条文档
     */
    public Object findOne() {
        // 设置查询条件参数
        int age = 22;
        // 创建条件对象
        Criteria criteria = Criteria.where("age").is(age);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询一条文档，如果查询结果中有多条文档，那么就取第一条
        User user = mongoTemplate.findOne(query, User.class, COLLECTION_NAME);
        // 输出结果
        log.info("用户信息：{}", user);
        return user;
    }

    /**
     * 根据【条件】查询集合中【符合条件】的文档，获取其【文档列表】
     *
     * @return 符合条件的文档列表
     */
    public Object findByCondition() {
        // 设置查询条件参数
        String sex = "女";
        // 创建条件对象
        Criteria criteria = Criteria.where("sex").is(sex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【条件】查询集合中【符合条件】的文档，获取其【文档列表】并【排序】
     *
     * @return 符合条件的文档列表
     */
    public Object findByConditionAndSort() {
        // 设置查询条件参数
        String sex = "男";
        String sort = "age";
        // 创建条件对象
        Criteria criteria = Criteria.where("sex").is(sex);
        // 创建查询对象，然后将条件对象添加到其中，然后根据指定字段进行排序
        Query query = new Query(criteria).with(Sort.by(sort));
        // 执行查询
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【单个条件】查询集合中的文档数据，并【按指定字段进行排序】与【限制指定数目】
     *
     * @return 符合条件的文档列表
     */
    public Object findByConditionAndSortLimit() {
        // 设置查询条件参数
        String sex = "男";
        String sort = "age";
        int limit = 2;
        // 创建条件对象
        Criteria criteria = Criteria.where("sex").is(sex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria).with(Sort.by(sort)).limit(limit);
        // 执行查询
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【单个条件】查询集合中的文档数据，并【按指定字段进行排序】与【并跳过指定数目】
     *
     * @return 符合条件的文档列表
     */
    public Object findByConditionAndSortSkip() {
        // 设置查询条件参数
        String sex = "男";
        String sort = "age";
        int skip = 1;
        // 创建条件对象
        Criteria criteria = Criteria.where("sex").is(sex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria).with(Sort.by(sort)).skip(skip);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 查询【存在指定字段名称】的文档数据
     *
     * @return 符合条件的文档列表
     */
    public Object findByExistsField() {
        // 设置查询条件参数
        String field = "sex";
        // 创建条件
        Criteria criteria = Criteria.where(field).exists(true);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【AND】关联多个查询条件，查询集合中的文档数据
     *
     * @return 符合条件的文档列表
     */
    public Object findByAndCondition() {
        // 设置查询条件参数
        String sex = "男";
        Integer age = 22;
        // 创建条件
        Criteria criteriaSex = Criteria.where("sex").is(sex);
        Criteria criteriaAge = Criteria.where("age").is(age);
        // 创建条件对象，将上面条件进行 AND 关联
        Criteria criteria = new Criteria().andOperator(criteriaSex, criteriaAge);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【OR】关联多个查询条件，查询集合中的文档数据
     *
     * @return 符合条件的文档列表
     */
    public Object findByOrCondition() {
        // 设置查询条件参数
        String sex = "男";
        int age = 22;
        // 创建条件
        Criteria criteriaSex = Criteria.where("sex").is(sex);
        Criteria criteriaAge = Criteria.where("age").is(age);
        // 创建条件对象，将上面条件进行 OR 关联
        Criteria criteria = new Criteria().orOperator(criteriaSex, criteriaAge);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【IN】关联多个查询条件，查询集合中的文档数据
     *
     * @return 符合条件的文档列表
     */
    public Object findByInCondition() {
        // 设置查询条件参数
        Integer[] ages = {20, 22, 25};
        // 创建条件
        List<Integer> ageList = Arrays.asList(ages);
        // 创建条件对象
        Criteria criteria = Criteria.where("age").in(ageList);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【逻辑运算符】查询集合中的文档数据
     *
     * @return 符合条件的文档列表
     */
    public Object findByOperator() {
        // 设置查询条件参数
        int min = 25;
        int max = 35;
        // 创建条件对象
        Criteria criteria = Criteria.where("age").gt(min).lte(max);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 根据【正则表达式】查询集合中的文档数据
     *
     * @return 符合条件的文档列表
     */
    public Object findByRegex() {
        // 设置查询条件参数
        String regex = "^zh*";
        // 创建条件对象
        Criteria criteria = Criteria.where("name").regex(regex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        List<User> documentList = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        // 输出结果
        for (User user : documentList) {
            log.info("用户信息：{}", user);
        }
        return documentList;
    }

    /**
     * 统计集合中符合【查询条件】的文档【数量】
     *
     * @return 符合条件的文档列表
     */
    public Object countNumber() {
        // 设置查询条件参数
        int age = 22;
        // 创建条件对象
        Criteria criteria = Criteria.where("age").is(age);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 查询并返回结果
        long count = mongoTemplate.count(query, User.class, COLLECTION_NAME);
        // 输出结果
        log.info("符合条件的文档数量：{}", count);
        return count;
    }

}
