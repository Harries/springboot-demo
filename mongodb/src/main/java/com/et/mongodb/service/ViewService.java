package com.et.mongodb.service;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 创建视图
     *
     * @return 创建视图结果
     */
    public Object createView() {
        // 设置视图名
        String newViewName = "usersView";
        // 设置获取数据的集合名称
        String collectionName = "users";
        // 定义视图的管道,可是设置视图显示的内容多个筛选条件
        List<Bson> pipeline = new ArrayList<>();
        // 设置条件，用于筛选集合中的文档数据，只有符合条件的才会映射到视图中
        pipeline.add(Document.parse("{\"$match\":{\"sex\":\"女\"}}"));
        // 执行创建视图
        mongoTemplate.getDb().createView(newViewName, collectionName, pipeline);
        // 检测新的集合是否存在，返回创建结果
        return mongoTemplate.collectionExists(newViewName) ? "创建视图成功" : "创建视图失败";
    }

    /**
     * 删除视图
     *
     * @return 删除视图结果
     */
    public Object dropView() {
        // 设置待删除的视图名称
        String viewName = "usersView";
        // 检测视图是否存在
        if (mongoTemplate.collectionExists(viewName)) {
            // 删除视图
            mongoTemplate.getDb().getCollection(viewName).drop();
            return "删除视图成功";
        }
        // 检测新的集合是否存在，返回创建结果
        return !mongoTemplate.collectionExists(viewName) ? "删除视图成功" : "删除视图失败";
    }

}
