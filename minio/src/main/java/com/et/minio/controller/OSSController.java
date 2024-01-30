package com.et.minio.controller;

import com.et.minio.config.MinioConfig;
import com.et.minio.util.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/oss")
public class OSSController {

    @Autowired
    private MinioUtils minioUtils;
    
    @Autowired
    private MinioConfig minioConfig;
    
    /**
     * 文件上传
     *
     * @param file
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
            //文件名
            String fileName = file.getOriginalFilename();
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            //类型
            String contentType = file.getContentType();
            minioUtils.uploadFile(minioConfig.getBucketName(), file, newFileName, contentType);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败");
            return "上传失败";
        }
    }

    /**
     * 删除
     *
     * @param fileName
     */
    @DeleteMapping("/")
    public void delete(@RequestParam("fileName") String fileName) {
        minioUtils.removeFile(minioConfig.getBucketName(), fileName);
    }

    /**
     * 获取文件信息
     *
     * @param fileName
     * @return
     */
    @GetMapping("/info")
    public String getFileStatusInfo(@RequestParam("fileName") String fileName) {
        return minioUtils.getFileStatusInfo(minioConfig.getBucketName(), fileName);
    }

    /**
     * 获取文件外链
     *
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    public String getPresignedObjectUrl(@RequestParam("fileName") String fileName) {
        return minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), fileName);
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            InputStream fileInputStream = minioUtils.getObject(minioConfig.getBucketName(), fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载失败");
        }
    }

}