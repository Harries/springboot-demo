package com.et.mysql;

import com.et.mysql.entity.UserPO;
import com.et.mysql.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class MysqlTests {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void testmysql(){
        for (UserPO userPO : userMapper.selectList(null)) {
            System.out.println(userPO.toString());
        }
    }
}