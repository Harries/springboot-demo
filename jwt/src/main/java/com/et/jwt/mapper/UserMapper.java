package com.et.jwt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.et.jwt.entity.User;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User>{
    @Select("SELECT * FROM jwt_user\n" +
            "      where\n" +
            "      username=#{username}")
    User findByUsername(@Param("username") String username);
    @Select("SELECT * FROM jwt_user WHERE id = #{Id}")
    User findUserById(@Param("Id") String Id);
}
