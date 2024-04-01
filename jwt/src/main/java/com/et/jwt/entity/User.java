package com.et.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date 2020-05-26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
//简单自定义一个实体类User
//使用lombok简化实体类的编写
public class User {
    String Id;
    String username;
    String password;
}
