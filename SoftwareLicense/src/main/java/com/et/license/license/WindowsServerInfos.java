/**
 * Project Name: true-license
 * File Name: WindowsServerInfos
 * Package Name: com.example.demo.entity
 * Date: 2020/10/10 11:14
 * Author: 方瑞冬
 */
package com.et.license.license;

import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author 方瑞冬
 * 获取客户 Windows 服务器的基本信息
 */
public class WindowsServerInfos extends AbstractServerInfos {
    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: WindowsServerInfos.java </p>
     * <p>方法描述: 获取 IP 地址 </p>
     * <p>创建时间: 2020/10/10 11:22 </p>
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
     * <p>文件名称: WindowsServerInfos.java </p>
     * <p>方法描述: 获取 Mac 地址 </p>
     * <p>创建时间: 2020/10/10 11:23 </p>
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
     * <p>文件名称: WindowsServerInfos.java </p>
     * <p>方法描述: 获取 CPU 序列号 </p>
     * <p>创建时间: 2020/10/10 11:23 </p>
     *
     * @return java.lang.String
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected String getCPUSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用 WMIC 获取 CPU 序列号
        Process process = Runtime.getRuntime().exec("wmic cpu get processorid");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if (scanner.hasNext()) {
            scanner.next();
        }
        if (scanner.hasNext()) {
            serialNumber = scanner.next().trim();
        }
        scanner.close();
        return serialNumber;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: WindowsServerInfos.java </p>
     * <p>方法描述: 获取主板序列号 </p>
     * <p>创建时间: 2020/10/10 11:23 </p>
     *
     * @return java.lang.String
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用 WMIC 获取主板序列号
        Process process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());
        if (scanner.hasNext()) {
            scanner.next();
        }
        if (scanner.hasNext()) {
            serialNumber = scanner.next().trim();
        }
        scanner.close();
        return serialNumber;
    }
}
