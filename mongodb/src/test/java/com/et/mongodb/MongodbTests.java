package com.et.mongodb;

import com.et.mongodb.entity.Status;
import com.et.mongodb.entity.User;
import com.mongodb.client.model.Indexes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class MongodbTests {
    private Logger log = LoggerFactory.getLogger(getClass());
    private static final String COLLECTION_NAME = "users";
    @Resource
    private MongoTemplate mongoTemplate;


    @Test
    public void createIndex() {
        String field = "name";
        // create index
        mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.ascending(field));
    }
    @Test
    public void insert() {
        // set user info
        User user = new User()
                .setId("10")
                .setAge(22)
                .setSex("man")
                .setRemake("nothing")
                .setSalary(1500)
                .setName("zhangsan")
                .setBirthday(new Date())
                .setStatus(new Status().setHeight(180).setWeight(150));
        // insert user info,throw exception when the row record is exist
        User newUser = mongoTemplate.insert(user, COLLECTION_NAME);
        // output result
        log.info("user info：{}", newUser);
    }


}

