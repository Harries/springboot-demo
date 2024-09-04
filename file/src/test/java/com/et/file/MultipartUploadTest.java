package com.et.file;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.RandomAccessFile;

public class MultipartUploadTest {

    @Test
    public void testUpload() throws Exception {
        String chunkFileFolder = "D:/tmp/";
        java.io.File file = new java.io.File("D:/SoftWare/oss-browser-win32-ia32.zip");
        long contentLength = file.length();
        // partSize:20MB
        long partSize = 20 * 1024 * 1024;
        // the last partSize may less  20MB
        long chunkFileNum = (long) Math.ceil(contentLength * 1.0 / partSize);
        RestTemplate restTemplate = new RestTemplate();

        try (RandomAccessFile raf_read = new RandomAccessFile(file, "r")) {
            // buffer
            byte[] b = new byte[1024];
            for (int i = 1; i <= chunkFileNum; i++) {
                // chunk
                java.io.File chunkFile = new java.io.File(chunkFileFolder + i);
                // write
                try (RandomAccessFile raf_write = new RandomAccessFile(chunkFile, "rw")) {
                    int len;
                    while ((len = raf_read.read(b)) != -1) {
                        raf_write.write(b, 0, len);
                        if (chunkFile.length() >= partSize) {
                            break;
                        }
                    }
                    // upload
                    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                    body.add("file", new FileSystemResource(chunkFile));
                    body.add("chunkNumber", i);
                    body.add("chunkSize", partSize);
                    body.add("currentChunkSize", chunkFile.length());
                    body.add("totalSize", contentLength);
                    body.add("filename", file.getName());
                    body.add("totalChunks", chunkFileNum);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                    String serverUrl = "http://localhost:8080/file/chunk";
                    ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
                    System.out.println("Response code: " + response.getStatusCode() + " Response body: " + response.getBody());
                } finally {
                    FileUtil.del(chunkFile);
                }
            }
        }
        // merge file
        String mergeUrl = "http://localhost:8080/file/merge?filename=" + file.getName();
        ResponseEntity<String> response = restTemplate.getForEntity(mergeUrl, String.class);
        System.out.println("Response code: " + response.getStatusCode() + " Response body: " + response.getBody());
    }
}
