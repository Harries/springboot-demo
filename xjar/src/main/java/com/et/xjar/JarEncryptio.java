package com.et.xjar;

import io.xjar.XCryptos;

public class JarEncryptio {
    public static void main(String[] args) throws Exception {
        // Spring-Boot Jar包加密
        XCryptos.encryption()
                .from("/Users/liuhaihua/IdeaProjects/springboot-demo/xjar/target/xjar-1.0-SNAPSHOT.jar")
                .use("passwad")
                .exclude("/static/**/*")
                .exclude("/templates/**/*")
                .exclude("/META-INF/resources/**/*")
                .to("/Users/liuhaihua/IdeaProjects/springboot-demo/xjar/target/xJarDir/xjar-encryption.jar");
        System.out.println("success");
    }
}

