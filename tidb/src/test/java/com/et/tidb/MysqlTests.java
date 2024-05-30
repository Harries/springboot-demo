package com.et.tidb;

import com.et.tidb.entity.UserPO;
import com.et.tidb.mapper.UserMapper;

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
        for (UserPO row : userMapper.selectList(null)) {
            System.out.println(row.toString());
        }
    }
}