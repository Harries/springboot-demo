package com.et.sftp;

import com.et.sftp.service.SftpService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * @author : qiushicai
 * @date : Created in 2020/11/20
 * @description :
 */
@SpringBootTest
public class SftpServiceTest {

    @Resource
    private SftpService sftpService;

    @Test
    void testExistFile() {
        boolean existFile = sftpService.existFile("/upload/home222.js");
        System.out.println(existFile);
    }


    @Test
    void listFileTest() {
        sftpService.listALLFile("/upload").stream().forEach(System.out::println);
    }

    @Test
    void upload2PathTest() throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File("D:\\tmp\\max.xml"));
        sftpService.upload(bytes, UUID.randomUUID().toString().concat(".xml"), "/tmp/audio/".concat(UUID.randomUUID().toString()));
    }

    @Test
    void testDownLoad() throws Exception {
        sftpService.downloadFile("/upload/home.js", "D:\\tmp\\c222c.js");
//
//        sftpService.uploadFile(new File("D:\\tmp\\cc.js"));


//        InputStream inputStream = sftpService.readFile("/upload/cc.js");
//
//        IOUtils.copy(inputStream, new FileOutputStream(new File("D:\\tmp\\" + UUID.randomUUID() + ".js")));

    }

    @Test
    void uploadFile() {
        sftpService.uploadFile(new File("D:\\tmp\\cc.js"));
    }


    @Test
    void nsltTest() {

        Arrays.asList(sftpService.nlstFile("/upload").split(",")).stream().forEach(System.out::println);
    }


    @Resource
    private MessagingTemplate messagingTemplate;

    @Resource
    private ApplicationContext applicationContext;

    @Test
    void testMessagingTemplate() throws InterruptedException {
        DirectChannel bean = (DirectChannel) applicationContext.getBean("abc");

        while (true) {
            messagingTemplate.convertAndSend(bean, "aaa".getBytes(), (Map<String, Object>) null);
            Thread.sleep(5000L);
        }
    }
}
