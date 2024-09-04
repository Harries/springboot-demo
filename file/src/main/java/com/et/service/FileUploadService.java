package com.et.service;

import com.et.bean.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileUploadService {

    void upload(MultipartFile[] files);

    List<FileInfo> list();

    Resource getFile(String fileName);
}
