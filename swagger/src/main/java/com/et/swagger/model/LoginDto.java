package com.et.swagger.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {
    @ApiModelProperty(value = "phone",required = true)
    private String phone;
    @ApiModelProperty(value = "password",required = true)
    private String password;
}