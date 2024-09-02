/**
 * Project Name: true-license
 * File Name: LicenseCreator
 * Package Name: com.example.demo.entity
 * Date: 2020/10/10 13:22
 * Author: 方瑞冬
 */
package com.et.license.license;

import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

/**
 * @author 方瑞冬
 * License 创建
 */
@Slf4j
public class LicenseCreator {
    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");
    private final LicenseCreatorParam param;

    public LicenseCreator(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreator.java </p>
     * <p>方法描述: 生成 License 证书 </p>
     * <p>创建时间: 2020/10/10 13:32 </p>
     *
     * @return boolean
     * @author 方瑞冬
     * @version 1.0
     */
    public boolean generateLicense() {
        try {
            LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();

            licenseManager.store(licenseContent, new File(param.getLicensePath()));

            return true;
        } catch (Exception e) {
            log.error(MessageFormat.format("证书生成失败：{0}", param), e);
            return false;
        }
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreator.java </p>
     * <p>方法描述: 初始化证书生成参数 </p>
     * <p>创建时间: 2020/10/10 13:33 </p>
     *
     * @return de.schlichtherle.license.LicenseParam
     * @author 方瑞冬
     * @version 1.0
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class
                , param.getPrivateKeysStorePath()
                , param.getPrivateAlias()
                , param.getStorePass()
                , param.getKeyPass());

        return new DefaultLicenseParam(param.getSubject()
                , preferences
                , privateStoreParam
                , cipherParam);
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreator.java </p>
     * <p>方法描述: 设置证书生成正文信息 </p>
     * <p>创建时间: 2020/10/10 13:34 </p>
     *
     * @return de.schlichtherle.license.LicenseContent
     * @author 方瑞冬
     * @version 1.0
     */
    private LicenseContent initLicenseContent() {
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        //扩展校验服务器硬件信息
        licenseContent.setExtra(param.getLicenseCheckModel());
        return licenseContent;
    }
}
