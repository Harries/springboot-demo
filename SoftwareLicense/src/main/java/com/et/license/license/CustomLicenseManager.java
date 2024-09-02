/**
 * Project Name: true-license
 * File Name: CustomLicenseManager
 * Package Name: com.example.demo.entity
 * Date: 2020/10/10 13:02
 * Author: 方瑞冬
 */
package com.et.license.license;

import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author 方瑞冬
 * 自定义 License 管理，创建、安装、校验等
 */
@Slf4j
public class CustomLicenseManager extends LicenseManager {
    /**
     * XML 编码
     */
    private static final String XML_CHARSET = "UTF-8";
    /**
     * 默认 BUFSIZE
     */
    private static final int DEFAULT_BUFSIZE = 8 * 1024;

    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 重写 License 创建 </p>
     * <p>创建时间: 2020/10/10 13:11 </p>
     *
     * @param content LicenseContent
     * @param notary  LicenseNotary
     * @return byte[]
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected synchronized byte[] create(LicenseContent content, LicenseNotary notary) throws Exception {
        initialize(content);
        this.validateCreate(content);
        final GenericCertificate certificate = notary.sign(content);
        return getPrivacyGuard().cert2key(certificate);
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 重写 License 安装 </p>
     * <p>创建时间: 2020/10/10 13:13 </p>
     *
     * @param key    key
     * @param notary LicenseNotary
     * @return de.schlichtherle.license.LicenseContent
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected synchronized LicenseContent install(final byte[] key, final LicenseNotary notary) throws Exception {
        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);

        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);

        return content;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 重写 License 校验 </p>
     * <p>创建时间: 2020/10/10 13:14 </p>
     *
     * @param notary LicenseNotary
     * @return de.schlichtherle.license.LicenseContent
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary) throws Exception {
        GenericCertificate certificate;

        // Load license key from preferences,
        final byte[] key = getLicenseKey();
        if (null == key) {
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }

        certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);

        return content;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 校验生成证书的参数信息 </p>
     * <p>创建时间: 2020/10/10 13:14 </p>
     *
     * @param content LicenseContent
     * @return void
     * @author 方瑞冬
     * @version 1.0
     */
    protected synchronized void validateCreate(final LicenseContent content) throws LicenseContentException {
        final Date now = new Date();
        final Date notBefore = content.getNotBefore();
        final Date notAfter = content.getNotAfter();
        if (null != notAfter && now.after(notAfter)) {
            throw new LicenseContentException("证书失效时间不能早于当前时间");
        }
        if (null != notBefore && null != notAfter && notAfter.before(notBefore)) {
            throw new LicenseContentException("证书生效时间不能晚于证书失效时间");
        }
        final String consumerType = content.getConsumerType();
        if (null == consumerType) {
            throw new LicenseContentException("用户类型不能为空");
        }
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 重写 License 验证 </p>
     * <p>创建时间: 2020/10/10 13:15 </p>
     *
     * @param content LicenseContent
     * @return void
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected synchronized void validate(final LicenseContent content) throws LicenseContentException {
        //1. 首先调用父类的validate方法
        super.validate(content);
        //2. 然后校验自定义的License参数
        //License中可被允许的参数信息
        LicenseCheckModel expectedCheckModel = (LicenseCheckModel) content.getExtra();
        //当前服务器真实的参数信息
        LicenseCheckModel serverCheckModel = getServerInfos();

        if (expectedCheckModel != null && serverCheckModel != null) {
            //校验IP地址
            if (!checkIpAddress(expectedCheckModel.getIpAddress(), serverCheckModel.getIpAddress())) {
                throw new LicenseContentException("当前服务器的IP没在授权范围内");
            }

            //校验Mac地址
            if (!checkIpAddress(expectedCheckModel.getMacAddress(), serverCheckModel.getMacAddress())) {
                throw new LicenseContentException("当前服务器的Mac地址没在授权范围内");
            }

            //校验主板序列号
            if (!checkSerial(expectedCheckModel.getMainBoardSerial(), serverCheckModel.getMainBoardSerial())) {
                throw new LicenseContentException("当前服务器的主板序列号没在授权范围内");
            }

            //校验CPU序列号
            if (!checkSerial(expectedCheckModel.getCpuSerial(), serverCheckModel.getCpuSerial())) {
                throw new LicenseContentException("当前服务器的CPU序列号没在授权范围内");
            }
        } else {
            throw new LicenseContentException("不能获取服务器硬件信息");
        }
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: XMLDecoder 解析 XML </p>
     * <p>创建时间: 2020/10/10 13:16 </p>
     *
     * @param encoded encoded
     * @return java.lang.Object
     * @author 方瑞冬
     * @version 1.0
     */
    private Object load(String encoded) {
        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)));

            decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE), null, null);

            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (decoder != null) {
                    decoder.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("XMLDecoder解析XML失败", e);
            }
        }
        return null;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 获取当前服务器需要额外校验的 License 参数 </p>
     * <p>创建时间: 2020/10/10 13:16 </p>
     *
     * @return com.example.demo.license.LicenseCheckModel
     * @author 方瑞冬
     * @version 1.0
     */
    private LicenseCheckModel getServerInfos() {
        //操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        AbstractServerInfos abstractServerInfos;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        } else {//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 校验当前服务器的IP/Mac地址是否在可被允许的IP范围内 </p>
     * <p>创建时间: 2020/10/10 13:17 </p>
     *
     * @param expectedList expectedList
     * @param serverList   serverList
     * @return boolean
     * @author 方瑞冬
     * @version 1.0
     */
    private boolean checkIpAddress(List<String> expectedList, List<String> serverList) {
        if (expectedList != null && expectedList.size() > 0) {
            if (serverList != null && serverList.size() > 0) {
                for (String expected : expectedList) {
                    if (serverList.contains(expected.trim())) {
                        return true;
                    }
                }
            }

            return false;
        } else {
            return true;
        }
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomLicenseManager.java </p>
     * <p>方法描述: 校验当前服务器硬件（主板、CPU 等）序列号是否在可允许范围内 </p>
     * <p>创建时间: 2020/10/10 13:18 </p>
     *
     * @param expectedSerial expectedSerial
     * @param serverSerial   serverSerial
     * @return boolean
     * @author 方瑞冬
     * @version 1.0
     */
    private boolean checkSerial(String expectedSerial, String serverSerial) {
        if (StringUtils.hasText(expectedSerial)) {
            if (StringUtils.hasText(serverSerial)) {
                return expectedSerial.equals(serverSerial);
            }
            return false;
        } else {
            return true;
        }
    }
}
