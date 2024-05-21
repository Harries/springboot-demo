package com.et.validation.entity;

import com.et.validation.validate.IpAddress;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
public class UserInfoReq {

    @NotNull(message = "id  is null")
    private Long id;

    @NotBlank(message = "username is null")
    private String username;

    @NotNull(message = "age is null")
    @Min(value = 1, message = "min age is 1 ")
    @Max(value = 200, message = "max age is 200")
    private Integer age;
    @Email(message = "email format limit")
    private String email;
    @IpAddress(message = "ip format  limit")
    private String ip;
}
