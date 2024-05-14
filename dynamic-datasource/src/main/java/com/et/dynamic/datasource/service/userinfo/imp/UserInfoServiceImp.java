package com.et.dynamic.datasource.service.userinfo.imp;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.et.dynamic.datasource.model.entity.UserInfo;
import com.et.dynamic.datasource.mapper.userinfo.UserInfoMapper;
import com.et.dynamic.datasource.service.userinfo.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author stopping
 * @since 2024-05-13
 */
@Service
@DS("slave_1")
public class UserInfoServiceImp extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;
    @Override
    @DS("master")
    public List<UserInfo> testQueryWrapper(int age) {
        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ge("age", age);
        List<UserInfo> userList = userInfoMapper.selectList(userQueryWrapper);
        return userList;
    }
}
