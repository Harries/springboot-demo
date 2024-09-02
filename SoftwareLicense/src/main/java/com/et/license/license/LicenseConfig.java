/**
 * Project Name: true-license
 * File Name: LicenseConfig
 * Package Name: com.example.demo.license
 * Date: 2020/10/10 14:03
 * Author: 方瑞冬
 */
package com.et.license.license;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 方瑞冬
 * 证书配置
 */
@Data
@Configuration
@ConfigurationProperties("license")
@Slf4j
public class LicenseConfig {
    /**
     * 证书 subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath;

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseConfig.java </p>
     * <p>方法描述: 把 LicenseVerify 类添加到 Spring 容器。在 LicenseVerify 从 Spring 容器添加或移除的时候调用证书安装或卸载 </p>
     * <p>创建时间: 2020/10/10 16:07 </p>
     *
     * @return com.example.demo.licensegenerate.LicenseVerify
     * @author 方瑞冬
     * @version 1.0
     */
    @Bean(initMethod = "installLicense", destroyMethod = "unInstallLicense")
    public LicenseVerify licenseVerify() {
        return new LicenseVerify(subject, publicAlias, storePass, licensePath, publicKeysStorePath);
    }
}
