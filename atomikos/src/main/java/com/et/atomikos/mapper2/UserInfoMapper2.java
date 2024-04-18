package com.et.atomikos.mapper2;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserInfoMapper2 {
    // 查询语句
    @Select("SELECT * FROM user_info WHERE username = #{username}")
    User findByName(@Param("username") String username);

    // 添加
    @Insert("INSERT INTO user_info(user_id,username, age) VALUES(#{userId},#{username}, #{age})")
    int insert(@Param("userId") String userId,@Param("username") String username, @Param("age") Integer age);
}