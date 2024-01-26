package com.et.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.mysql.entity.UserPO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserPO> {
}