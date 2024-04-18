package com.et.atomikos.mapper1;

import com.et.atomikos.mapper1.UserInfoMapper1;
import com.et.atomikos.mapper2.UserInfoMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyService1 {

    @Autowired
    private UserInfoMapper1 userInfoMapper1;
    @Autowired
    private UserInfoMapper2 userInfoMapper2;


    @Transactional
    public int insert(String userId,String username, Integer age) {
        int insert = userInfoMapper1.insert(userId,username, age);
        int i = 1 / age;// if age is zero  ,then a error will be happened.
        return insert;
    }

    @Transactional
    public int insertDb1AndDb2(String userId,String username, Integer age) {
        int insert = userInfoMapper1.insert(userId,username, age);
        int insert2 = userInfoMapper2.insert(userId,username, age);
        int i = 1 / age;// if age is zero  ,then a error will be happened.
        return insert + insert2;
    }


}