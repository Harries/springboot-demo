package com.et.mybaties.plus.service.userInfo.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.et.mybaties.plus.model.entity.UserInfo;
import com.et.mybaties.plus.mapper.userInfo.UserInfoMapper;
import com.et.mybaties.plus.service.userInfo.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stopping
 * @since 2024-04-22
 */
@Service
public class UserInfoServiceImp extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public List<UserInfo> testQueryWrapper(int age) {
        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ge("age", age);
        //trainList为空不报错
        List<UserInfo> userList = userInfoMapper.selectList(userQueryWrapper);
        return userList;
    }
}
