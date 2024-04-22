package com.et.mybaties.plus.service.userInfo;

import com.et.mybaties.plus.model.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stopping
 * @since 2024-04-22
 */
public interface UserInfoService extends IService<UserInfo> {
    public List<UserInfo> testQueryWrapper(int age);

}
