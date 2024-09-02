/**
 * Project Name: true-license
 * File Name: LicenseCreatorServiceImpl
 * Package Name: com.example.demo.service.impl
 * Date: 2020/10/10 13:44
 * Author: 方瑞冬
 */
package com.et.license.service.impl;

import com.et.license.license.*;
import com.et.license.service.LicenseCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 方瑞冬
 */
@Service
public class LicenseCreatorServiceImpl implements LicenseCreatorService {
    @Autowired
    private LicenseConfig licenseConfig;

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreatorServiceImpl.java </p>
     * <p>方法描述: 获取服务器硬件信息 </p>
     * <p>创建时间: 2020/10/10 13:46 </p>
     *
     * @param osName 系统名称
     * @return com.example.demo.license.LicenseCheckModel
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    public LicenseCheckModel getServerInfos(String osName) {
        //操作系统类型
        if (StringUtils.isEmpty(osName)) {
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();

        AbstractServerInfos abstractServerInfos = null;

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
     * <p>文件名称: LicenseCreatorServiceImpl.java </p>
     * <p>方法描述: 生成证书 </p>
     * <p>创建时间: 2020/10/10 13:46 </p>
     *
     * @param param 证书创建参数
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    public Map<String, Object> generateLicense(LicenseCreatorParam param) {
        Map<String, Object> resultMap = new HashMap<>(2);

        if (StringUtils.isEmpty(param.getLicensePath())) {
            param.setLicensePath(licenseConfig.getLicensePath());
        }

        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();

        if (result) {
            resultMap.put("result", "ok");
            resultMap.put("msg", param);
        } else {
            resultMap.put("result", "error");
            resultMap.put("msg", "证书文件生成失败！");
        }

        return resultMap;
    }
}
