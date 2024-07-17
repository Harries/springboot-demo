package com.et.sftp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;

import javax.annotation.Resource;

/**
 * @author : qiushicai
 * @date : Created in 2020/11/20
 * @description :
 */

@SpringBootTest
public class SftpRemoteFileTemplateTest {

    @Resource
    private SftpRemoteFileTemplate sftpRemoteFileTemplate;

    @Test
    void rmTest() {
        sftpRemoteFileTemplate.execute(session -> {
            boolean exists = session.exists("/tmp/foo/home.js");
            System.out.println(exists);
            return exists;
        });
    }


    @Test
    void uploadTest() {
        sftpRemoteFileTemplate.execute(session -> {
            boolean exists = session.exists("/tmp/foo/home.js");
            System.out.println(exists);
            return exists;
        });
    }
}
