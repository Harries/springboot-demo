package com.et.tidb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.tidb.entity.UserPO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserPO> {
}