package demo.et.mongodb.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class QueryCollectionService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 获取【集合名称】列表
     *
     * @return 集合名称列表
     */
    public Object getCollectionNames() {
        // 执行获取集合名称列表
        return mongoTemplate.getCollectionNames();
    }

    /**
     * 检测集合【是否存在】
     *
     * @return 集合是否存在
     */
    public boolean collectionExists() {
        // 设置集合名称
        String collectionName = "users";
        // 检测新的集合是否存在，返回检测结果
        return mongoTemplate.collectionExists(collectionName);
    }

}
