/**
 * Project Name: true-license
 * File Name: CustomKeyStoreParam
 * Package Name: com.example.demo.entity
 * Date: 2020/10/10 13:30
 * Author: 方瑞冬
 */
package com.et.license.license;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 方瑞冬
 * 自定义 KeyStoreParam，用于将公私钥存储文件存放到其他磁盘位置而不是项目中
 * 实际使用的时候公钥大部分都不会放在项目中的
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {
    /**
     * 公钥/私钥在磁盘上的存储路径
     */
    private final String storePath;
    private final String alias;
    private final String storePwd;
    private final String keyPwd;

    public CustomKeyStoreParam(Class clazz, String resource, String alias, String storePwd, String keyPwd) {
        super(clazz, resource);
        this.storePath = resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    /**
     * <p>项目名称: true-license-demo </p>
     * <p>文件名称: CustomKeyStoreParam.java </p>
     * <p>方法描述: 用于将公私钥存储文件存放到其他磁盘位置而不是项目中，AbstractKeyStoreParam 里面的 getStream() 方法默认文件是存储的项目中 </p>
     * <p>创建时间: 2020/10/10 13:31 </p>
     *
     * @param
     * @return java.io.InputStream
     * @author 方瑞冬
     * @version 1.0
     */
    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream(new File(storePath));
    }
}
