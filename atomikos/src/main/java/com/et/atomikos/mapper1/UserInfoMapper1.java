package com.et.atomikos.mapper1;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserInfoMapper1 {
    // query
    @Select("SELECT * FROM user_info WHERE username = #{username}")
    User findByName(@Param("username") String username);

    // add
    @Insert("INSERT INTO user_info(user_id,username, age) VALUES(#{userId},#{username}, #{age})")
    int insert(@Param("userId") String userId,@Param("username") String username, @Param("age") Integer age);
}