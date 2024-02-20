package demo.et.mongodb.service;

import demo.et.mongodb.entity.Status;
import demo.et.mongodb.entity.User;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class SaveService {

    /** 设置集合名称 */
    private static final String COLLECTION_NAME = "users";

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 存储【一条】用户信息，如果文档信息已经【存在就执行更新】
     *
     * @return 存储的文档信息
     */
    public Object save() {
        // 设置用户信息
        User user = new User()
                .setId("13")
                .setAge(22)
                .setSex("男")
                .setRemake("无")
                .setSalary(2800)
                .setName("kuiba")
                .setBirthday(new Date())
                .setStatus(new Status().setHeight(169).setWeight(150));
        // 存储用户信息,如果文档信息已经存在就执行更新
        User newUser = mongoTemplate.save(user, COLLECTION_NAME);
        // 输出存储结果
        log.info("存储的用户信息为：{}", newUser);
        return newUser;
    }

}
