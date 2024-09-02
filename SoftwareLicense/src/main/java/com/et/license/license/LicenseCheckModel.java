/**
 * Project Name: true-license
 * File Name: LicenseCheckModel
 * Package Name: com.example.demo.entity
 * Date: 2020/10/10 10:42
 * Author: 方瑞冬
 */
package com.et.license.license;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 方瑞冬
 * License 服务器校验参数
 */
@Data
public class LicenseCheckModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 可被允许的 IP  地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的 MAC 地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的 CPU 序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;
}
