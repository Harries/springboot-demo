package com.et.mongodb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Slf4j
@Service
public class RemoveIndexService {

    @Resource
    private MongoTemplate mongoTemplate;

    /** 设置集合名称 */
    private static final String COLLECTION_NAME = "users";

    /**
     * 根据索引名称移除索引
     */
    public void removeIndex() {
        // 设置索引名称
        String indexName = "name_1";
        // 删除集合中某个索引
        mongoTemplate.getCollection(COLLECTION_NAME).dropIndex(indexName);
    }

    /**
     * 移除全部索引
     */
    public void removeIndexAll() {
        // 删除集合中全部索引
        mongoTemplate.getCollection(COLLECTION_NAME).dropIndexes();
    }

}
