/**
 * Project Name: true-license
 * File Name: LinuxServerInfos
 * Package Name: com.example.demo.entity
 * Date: 2020/10/10 11:05
 * Author: 方瑞冬
 */
package com.et.license.license;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 方瑞冬
 * 获取客户 Linux 服务器的基本信息
 */
public class LinuxServerInfos extends AbstractServerInfos {
    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LinuxServerInfos.java </p>
     * <p>方法描述: 获取 IP 地址 </p>
     * <p>创建时间: 2020/10/10 11:07 </p>
     *
     * @return java.util.List<java.lang.String>
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;

        //获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if (inetAddresses != null && inetAddresses.size() > 0) {
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }

        return result;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LinuxServerInfos.java </p>
     * <p>方法描述: 获取 Mac 地址 </p>
     * <p>创建时间: 2020/10/10 11:08 </p>
     *
     * @return java.util.List<java.lang.String>
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected List<String> getMacAddress() throws Exception {
        List<String> result = null;

        //1. 获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if (inetAddresses != null && inetAddresses.size() > 0) {
            //2. 获取所有网络接口的 Mac 地址
            result = inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
        }

        return result;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LinuxServerInfos.java </p>
     * <p>方法描述: 获取 CPU 序列号 </p>
     * <p>创建时间: 2020/10/10 11:08 </p>
     *
     * @return java.lang.String
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected String getCPUSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用 dmidecode 命令获取 CPU 序列号
        String[] shell = {"/bin/bash", "-c", "dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = reader.readLine().trim();
        if (StringUtils.hasText(line)) {
            serialNumber = line;
        }

        reader.close();
        return serialNumber;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: LinuxServerInfos.java </p>
     * <p>方法描述: 获取主板序列号 </p>
     * <p>创建时间: 2020/10/10 11:08 </p>
     *
     * @return java.lang.String
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用 dmidecode 命令获取主板序列号
        String[] shell = {"/bin/bash", "-c", "dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine().trim();
        if (StringUtils.hasText(line)) {
            serialNumber = line;
        }
        reader.close();
        return serialNumber;
    }
}
