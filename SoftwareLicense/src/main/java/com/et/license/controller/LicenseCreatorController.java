/**
 * Project Name: true-license
 * File Name: LicenseCreatorController
 * Package Name: com.example.demo.controller
 * Date: 2020/10/10 13:36
 * Author: 方瑞冬
 */
package com.et.license.controller;

import com.et.license.license.LicenseCheckModel;
import com.et.license.license.LicenseCreatorParam;
import com.et.license.service.LicenseCreatorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 方瑞冬
 */
@RestController
@RequestMapping("/license")
public class LicenseCreatorController {
    @Autowired
    private LicenseCreatorService licenseCreatorService;

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreatorController.java </p>
     * <p>方法描述: 获取服务器硬件信息 </p>
     * <p>创建时间: 2020/10/10 13:39 </p>
     *
     * @param osName 系统名称
     * @return com.example.demo.license.LicenseCheckModel
     * @author 方瑞冬
     * @version 1.0
     */
    @GetMapping("/getServerInfos")
    public LicenseCheckModel getServerInfos(@RequestParam String osName) {
        return licenseCreatorService.getServerInfos(osName);
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LicenseCreatorController.java </p>
     * <p>方法描述: 生成证书 </p>
     * <p>创建时间: 2020/10/10 13:42 </p>
     *
     * @param param 证书创建参数
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author 方瑞冬
     * @version 1.0
     */
    @PostMapping("/generateLicense")
    public Map<String, Object> generateLicense(@RequestBody LicenseCreatorParam param) {
        return licenseCreatorService.generateLicense(param);
    }
}
