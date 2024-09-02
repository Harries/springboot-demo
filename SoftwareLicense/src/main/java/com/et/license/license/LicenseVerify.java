/**
 * Project Name: true-license
 * File Name: LicenseVerify
 * Package Name: com.example.demo.licenseverify
 * Date: 2020/10/10 15:46
 * Author: 方瑞冬
 */
package com.et.license.license;

import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

/**
 * @author 方瑞冬
 * Lincense 校验、安装、卸载
 */
@Slf4j
public class LicenseVerify {
    /**
     * 证书subject
     */
    private final String subject;
    /**
     * 公钥别称
     */
    private final String publicAlias;
    /**
     * 访问公钥库的密码
     */
    private final String storePass;
    /**
     * 证书生成路径
     */
    private final String licensePath;
    /**
     * 密钥库存储路径
     */
    private final String publicKeysStorePath;
    /**
     * LicenseManager
     */
    private LicenseManager licenseManager;
    /**
     * 证书是否安装成功标记
     */
    private boolean installSuccess;

    public LicenseVerify(String subject, String publicAlias, String storePass, String licensePath, String publicKeysStorePath) {
        this.subject = subject;
        this.publicAlias = publicAlias;
        this.storePass = storePass;
        this.licensePath = licensePath;
        this.publicKeysStorePath = publicKeysStorePath;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseVerify.java </p>
     * <p>方法描述: 安装 License 证书，读取证书相关的信息, 在 Bean 加入容器的时候自动调用 </p>
     * <p>创建时间: 2020/10/10 15:58 </p>
     *
     * @return void
     * @author 方瑞冬
     * @version 1.0
     */
    public void installLicense() {
        try {
            Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

            CipherParam cipherParam = new DefaultCipherParam(storePass);

            KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class,
                    publicKeysStorePath,
                    publicAlias,
                    storePass,
                    null);
            LicenseParam licenseParam = new DefaultLicenseParam(subject, preferences, publicStoreParam, cipherParam);

            licenseManager = new CustomLicenseManager(licenseParam);
            licenseManager.uninstall();
            LicenseContent licenseContent = licenseManager.install(new File(licensePath));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            installSuccess = true;
            log.info("------------------------------- 证书安装成功 -------------------------------");
            log.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}", format.format(licenseContent.getNotBefore()), format.format(licenseContent.getNotAfter())));
        } catch (Exception e) {
            installSuccess = false;
            log.error("------------------------------- 证书安装失败 -------------------------------");
            log.error(e.getMessage(), e);
        }
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseVerify.java </p>
     * <p>方法描述: 卸载证书，在 Bean 从容器移除的时候自动调用 </p>
     * <p>创建时间: 2020/10/10 15:58 </p>
     *
     * @return void
     * @author 方瑞冬
     * @version 1.0
     */
    public void unInstallLicense() {
        if (installSuccess) {
            try {
                licenseManager.uninstall();
                log.info("------------------------------- 证书卸载成功 -------------------------------");
            } catch (Exception e) {
                log.error("------------------------------- 证书卸载失败 -------------------------------");
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseVerify.java </p>
     * <p>方法描述:  </p>
     * <p>创建时间: 2020/10/10 16:00 </p>
     *
     * @return boolean
     * @author 方瑞冬
     * @version 1.0
     */
    public boolean verify() {
        try {
            LicenseContent licenseContent = licenseManager.verify();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info(MessageFormat.format("证书有效期：{0} - {1}", format.format(licenseContent.getNotBefore()), format.format(licenseContent.getNotAfter())));
            return true;
        } catch (Exception e) {
            log.error("证书校验失败" + e.getMessage(), e);
            return false;
        }
    }
}
