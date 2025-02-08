package com.et.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        amazonS3.putObject(bucketName, fileName, inputStream, null);
        return fileName;
    }

    public List<String> listFiles() {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        List<String> fileNames = new ArrayList<>();
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            fileNames.add(objectSummary.getKey());
        }
        return fileNames;
    }
}