/**
 * Project Name: true-license
 * File Name: AbstractServerInfos
 * Package Name: com.example.demo.abstracts
 * Date: 2020/10/10 10:56
 * Author: 方瑞冬
 */
package com.et.license.license;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 方瑞冬
 * 获取服务器的硬件信息
 */
@Slf4j
public abstract class AbstractServerInfos {
    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: AbstractServerInfos.java </p>
     * <p>方法描述: 组装需要额外校验的License参数 </p>
     * <p>创建时间: 2020/10/10 11:01 </p>
     *
     * @return LicenseCheckModel
     * @author 方瑞冬
     * @version 1.0
     */
    public LicenseCheckModel getServerInfos() {
        LicenseCheckModel result = new LicenseCheckModel();

        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
        } catch (Exception e) {
            log.error("获取服务器硬件信息失败", e);
        }

        return result;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: AbstractServerInfos.java </p>
     * <p>方法描述: 获取 IP 地址 </p>
     * <p>创建时间: 2020/10/10 11:02 </p>
     *
     * @return java.util.List<java.lang.String>
     * @author 方瑞冬
     * @version 1.0
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: AbstractServerInfos.java </p>
     * <p>方法描述: 获取 Mac 地址 </p>
     * <p>创建时间: 2020/10/10 11:02 </p>
     *
     * @return java.util.List<java.lang.String>
     * @author 方瑞冬
     * @version 1.0
     */
    protected abstract List<String> getMacAddress() throws Exception;

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: AbstractServerInfos.java </p>
     * <p>方法描述: 获取 CPU 序列号 </p>
     * <p>创建时间: 2020/10/10 11:02 </p>
     *
     * @return java.lang.String
     * @author 方瑞冬
     * @version 1.0
     */
    protected abstract String getCPUSerial() throws Exception;

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: AbstractServerInfos.java </p>
     * <p>方法描述: 获取主板序列号 </p>
     * <p>创建时间: 2020/10/10 11:02 </p>
     *
     * @return java.lang.String
     * @author 方瑞冬
     * @version 1.0
     */
    protected abstract String getMainBoardSerial() throws Exception;

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: AbstractServerInfos.java </p>
     * <p>方法描述: 获取当前服务器所有符合条件的 InetAddress </p>
     * <p>创建时间: 2020/10/10 11:02 </p>
     *
     * @return java.util.List<java.net.InetAddress>
     * @author 方瑞冬
     * @version 1.0
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);

        // 遍历所有的网络接口
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = networkInterfaces.nextElement();
            // 在所有的接口下再遍历 IP
            for (Enumeration<InetAddress> inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = inetAddresses.nextElement();

                //排除 LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress 类型的 IP 地址
                if (!inetAddr.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()) {
                    result.add(inetAddr);
                }
            }
        }
        return result;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: AbstractServerInfos.java </p>
     * <p>方法描述: 获取某个网络接口的 Mac 地址 </p>
     * <p>创建时间: 2020/10/10 11:03 </p>
     *
     * @param inetAddr inetAddr
     * @return java.lang.String
     * @author 方瑞冬
     * @version 1.0
     */
    protected String getMacByInetAddress(InetAddress inetAddr) {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    stringBuilder.append("-");
                }
                //将十六进制 byte 转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if (temp.length() == 1) {
                    stringBuilder.append("0").append(temp);
                } else {
                    stringBuilder.append(temp);
                }
            }
            return stringBuilder.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
