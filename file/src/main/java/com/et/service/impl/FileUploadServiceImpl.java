package com.et.service.impl;

import com.et.bean.FileInfo;
import com.et.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${upload.path:/data/upload/}")
    private String filePath;

    private static final List<FileInfo> FILE_STORAGE = new CopyOnWriteArrayList<>();

    @Override
    public void upload(MultipartFile[] files) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            boolean match = FILE_STORAGE.stream().anyMatch(fileInfo -> fileInfo.getFileName().equals(fileName));
            if (match) {
                throw new RuntimeException("File [ " + fileName + " ] already exist");
            }

            String currentTime = simpleDateFormat.format(new Date());
            try (InputStream in = file.getInputStream();
                 OutputStream out = Files.newOutputStream(Paths.get(filePath + fileName))) {
                FileCopyUtils.copy(in, out);
            } catch (IOException e) {
                log.error("File [{}] upload failed", fileName, e);
                throw new RuntimeException(e);
            }
            FileInfo fileInfo = new FileInfo().setFileName(fileName).setUploadTime(currentTime);
            FILE_STORAGE.add(fileInfo);
        }
    }

    @Override
    public List<FileInfo> list() {
        return FILE_STORAGE;
    }

    @Override
    public Resource getFile(String fileName) {
        FILE_STORAGE.stream()
                .filter(info -> info.getFileName().equals(fileName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("File [ " + fileName + " ] not exist"));
        File file = new File(filePath + fileName);
        return new FileSystemResource(file);
    }
}
