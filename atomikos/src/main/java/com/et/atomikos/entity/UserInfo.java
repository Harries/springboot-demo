package com.et.atomikos.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName UserInfo
 * @Description todo
 * @date 2024年04月18日 13:17
 */
@Data
public class UserInfo {
    private String userId;
    private String username;
    private int age;
    private int gender;
    private String remark;
    private Date createTime;
    private String createId;
    private Date updateTime;
    private String updateId;
    private int enabled;

}
