package com.et.validation.entity;

import com.et.validation.validate.IpAddress;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
public class UserInfoReq {

    @NotNull(message = "id不能为null")
    private Long id;

    @NotBlank(message = "username不能为空")
    private String username;

    @NotNull(message = "age不能为null")
    @Min(value = 1, message = "年龄不符合要求")
    @Max(value = 200, message = "年龄不符合要求")
    private Integer age;
    @Email(message = "邮箱不符合规范")
    private String email;
    @IpAddress(message = "ip不符合规范")
    private String ip;
}
