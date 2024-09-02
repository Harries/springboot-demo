/**
 * Project Name: true-license
 * File Name: LicenseCreatorService
 * Package Name: com.example.demo.service
 * Date: 2020/10/10 13:44
 * Author: 方瑞冬
 */
package com.et.license.service;



import com.et.license.license.LicenseCheckModel;
import com.et.license.license.LicenseCreatorParam;

import java.util.Map;

/**
 * @author 方瑞冬
 */
public interface LicenseCreatorService {
    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreatorService.java </p>
     * <p>方法描述: 获取服务器硬件信息 </p>
     * <p>创建时间: 2020/10/10 13:45 </p>
     *
     * @param osName 系统名称
     * @return com.example.demo.license.LicenseCheckModel
     * @author 方瑞冬
     * @version 1.0
     */
    LicenseCheckModel getServerInfos(String osName);

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreatorService.java </p>
     * <p>方法描述: 生成证书 </p>
     * <p>创建时间: 2020/10/10 13:45 </p>
     *
     * @param param 证书创建参数
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author 方瑞冬
     * @version 1.0
     */
    Map<String, Object> generateLicense(LicenseCreatorParam param);
}
