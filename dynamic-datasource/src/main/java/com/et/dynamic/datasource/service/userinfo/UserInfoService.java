package com.et.dynamic.datasource.service.userinfo;

import com.et.dynamic.datasource.model.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stopping
 * @since 2024-05-13
 */
public interface UserInfoService extends IService<UserInfo> {
    public List<UserInfo> testQueryWrapper(int age);
}
