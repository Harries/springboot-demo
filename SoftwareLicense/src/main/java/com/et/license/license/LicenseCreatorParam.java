/**
 * Project Name: true-license
 * File Name: LicenseCreatorParam
 * Package Name: com.example.demo.entity
 * Date: 2020/10/10 10:49
 * Author: 方瑞冬
 */
package com.et.license.license;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 方瑞冬
 * License 创建参数
 */
@Data
public class LicenseCreatorParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 证书 subject
     */
    private String subject;

    /**
     * 密钥（私钥）别称
     */
    private String privateAlias;

    /**
     * 密钥（私钥）密码（需要妥善保管，不能让使用者知道）
     */
    private String keyPass;

    /**
     * 访问秘钥库（私钥）的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥（私钥）库存储路径
     */
    private String privateKeysStorePath;

    /**
     * 证书生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();

    /**
     * 证书失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**
     * 用户类型
     */
    private String consumerType = "user";

    /**
     * 用户数量
     */
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 额外的 License 校验参数
     */
    private LicenseCheckModel licenseCheckModel;
}
