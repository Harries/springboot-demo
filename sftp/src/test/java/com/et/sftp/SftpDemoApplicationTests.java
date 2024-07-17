package com.et.sftp;

import com.example.sftpdemo.config.SftpConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class SftpDemoApplicationTests {

    @Autowired
    private SftpConfiguration.SftpGateway sftpGateway;

    @Test
    void contextLoads() {

    }


    @Test
    void testListFiles(){
        sftpGateway.listFile("/tmp/foo").stream().forEach((f)->{
            System.out.println(f);
        });
    }

    @Test
    void testUpload(){
        sftpGateway.upload(new File("D:\\dev-ide\\self_m2\\ali-settings.xml"));
    }

    @Test
    void testDownload(){
        List<File> downloadFiles = sftpGateway.downloadFiles("/tmp/foo");
        downloadFiles.stream().forEach((f)->{
            System.out.println(f.getName());
        });
    }


}
