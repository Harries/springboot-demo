package com.et.dynamic.datasource.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author stopping
 * @since 2024-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id")
    private String userId;

    @TableField("username")
    private String username;

    @TableField("age")
    private Integer age;

    @TableField("gender")
    private Boolean gender;

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_id")
    private String createId;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_id")
    private String updateId;

    @TableField("enabled")
    private Boolean enabled;


}
