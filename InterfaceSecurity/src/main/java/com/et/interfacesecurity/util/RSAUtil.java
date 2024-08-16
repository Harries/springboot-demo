package com.et.interfacesecurity.util;

import com.et.interfacesecurity.entity.Entity;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: 小新
 * @Date: 2024/4/3 9:56
 *  RSA 数据加签验签 加密解密
 */
public class RSAUtil {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    private String privateKey;
    private String publicKey;

    public static void main(String[] args) {
        Entity entity = new Entity();
        // 使用RSA算法生成 公钥与私钥, 生成的公私钥 是一一对应的。
        createRSAKey(entity);
        String body = "加签";
        // 将入参数据以及私钥进行数字加签
        String sign = sign(body, entity.getPrivateKey());
        // 根据入参数据以及公钥进行验证签名，若入参数据body被修改或者秘钥不正确都会导致验签失败；例如加签使用body，验签使用body2则导致验签失败
        boolean verifyFlag = verify(body,entity.getPublicKey(), sign);
        if (verifyFlag) {
            logger.info("验签成功");
        } else {
            logger.info("验签失败");
        }
    }

    /**
     * 生成对应的 与我通信的公钥和私钥
     * @return
     */
    public static void createRSAKey(Entity entity) {
        try {
            // 创建KeyPairGenerator 指定算法为RSA，用于生成对应的公钥和私钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 指定字节长度
            keyPairGenerator.initialize(1024);
            // 秘钥生成器
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            // 进行Base64编码存入
            String clientPublicKey = Base64.encodeBase64String(publicKey.getEncoded());
            logger.info("生成的clientPublicKey是: {}", clientPublicKey);
            entity.setPublicKey(clientPublicKey);
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            // 进行Base64编码存入
            String clientPrivateKey = Base64.encodeBase64String(privateKey.getEncoded());
            logger.info("生成的clientPrivateKey是: {}", clientPrivateKey);
            entity.setPrivateKey(clientPrivateKey);
        } catch (Exception e) {
            logger.error("生成秘钥失败");
            e.printStackTrace();
        }
    }

    /**
     * 利用私钥信息生成数字签名
     * @param data 入参数据body
     * @param privateKey 私钥
     * @return
     */
    public static String sign(String data, String privateKey) {
        try {
            // 入参数据body字节数组
            byte[] dataBytes = data.getBytes();
            // 获取私钥秘钥字节数组
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            // 使用给定的编码密钥创建一个新的PKCS8EncodedKeySpec。
            // PKCS8EncodedKeySpec 是 PKCS#8标准作为密钥规范管理的编码格式
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            // 实例化KeyFactory,指定为加密算法 为 RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 获得PrivateKey对象
            PrivateKey privateKey1 = keyFactory.generatePrivate(keySpec);
            // 用私钥对信息生成数字签名，指定签名算法为 MD5withRSA
            Signature signature = Signature.getInstance("MD5withRSA");
            // 初始化签名
            signature.initSign(privateKey1);
            // 数据body带入
            signature.update(dataBytes);
            // 对签名进行Base64编码
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用公钥校验数字签名
     * @param data 入参数据body
     * @param publicKey 公钥
     * @param sign 签名
     * @return
     */
    public static boolean verify(String data, String publicKey, String sign) {
        try {
            // 入参数据body字节数组
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            // 获取公钥秘钥字节数组
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            // 使用给定的编码密钥创建一个新的X509EncodedKeySpec
            // X509EncodedKeySpec是基于X.509证书提前的公钥，一种java秘钥规范
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            // 实例化KeyFactory,指定为加密算法 为 RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 获取publicKey对象
            PublicKey publicKey1 = keyFactory.generatePublic(x509EncodedKeySpec);
            // 用私钥对信息生成数字签名，指定签名算法为 MD5withRSA
            Signature signature = Signature.getInstance("MD5withRSA");
            // 带入公钥进行验证
            signature.initVerify(publicKey1);
            // 数据body带入
            signature.update(dataBytes);
            // 验证签名
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 使用公钥对明文进行加密
     *
     * @param plainText 明文字符串
     * @param publicKey 公钥字符串（Base64编码）
     * @return 加密后的数据（字节数组形式）
     * @throws Exception 加密过程中发生的异常
     */
    public static byte[] encryptWithPublicKey(String plainText, String publicKey) throws Exception {
        // 解码公钥字符串
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        // 生成X509EncodedKeySpec
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // 实例化KeyFactory并指定为RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 生成PublicKey对象
        PublicKey rsaPublicKey = keyFactory.generatePublic(keySpec);
        // 实例化Cipher并指定为RSA/ECB/PKCS1Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // 使用公钥初始化Cipher为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        // 执行加密操作
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 使用私钥对密文进行解密
     *
     * @param encryptedData 加密后的数据（字节数组形式）
     * @param privateKey    私钥字符串（Base64编码）
     * @return 解密后的明文字符串
     * @throws Exception 解密过程中发生的异常
     */
    public static String decryptWithPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        // 解码私钥字符串
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        // 生成PKCS8EncodedKeySpec
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        // 实例化KeyFactory并指定为RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 生成PrivateKey对象
        PrivateKey rsaPrivateKey = keyFactory.generatePrivate(keySpec);
        // 实例化Cipher并指定为RSA/ECB/PKCS1Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // 使用私钥初始化Cipher为解密模式
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        // 执行解密操作
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        // 将解密后的字节数组转换为字符串（使用UTF-8编码）
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 使用私钥对数据进行加密
     *
     * @param data 明文数据（字符串形式）
     * @param privateKey 私钥字符串（Base64编码）
     * @return 加密后的密文（字节数组）
     * @throws Exception 加密过程中发生的异常
     */
    public static byte[] encryptWithPrivateKey(String data, String privateKey) throws Exception {
        // 解码私钥字符串
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        // 生成PKCS8EncodedKeySpec
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        // 实例化KeyFactory并指定为RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 生成PrivateKey对象
        PrivateKey rsaPrivateKey = keyFactory.generatePrivate(keySpec);
        // 实例化Cipher并指定为RSA/ECB/PKCS1Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // 使用私钥初始化Cipher为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        // 执行加密操作
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 使用公钥对数据进行解密
     *
     * @param encryptedData 加密后的数据（字节数组形式）
     * @param publicKey 公钥字符串（Base64编码）
     * @return 解密后的明文数据（字符串）
     * @throws Exception 解密过程中发生的异常
     */
    public static String decryptWithPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        // 解码公钥字符串
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        // 生成X509EncodedKeySpec
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // 实例化KeyFactory并指定为RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 生成PublicKey对象
        PublicKey rsaPublicKey = keyFactory.generatePublic(keySpec);
        // 实例化Cipher并指定为RSA/ECB/PKCS1Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // 使用公钥初始化Cipher为解密模式
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        // 执行解密操作
        byte[] decryptedBytes = cipher.doFinal(encryptedData);
        // 将解密后的字节数组转换为字符串（使用UTF-8编码）
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
