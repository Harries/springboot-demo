package com.et.sftp.service;

import org.springframework.integration.file.remote.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface SftpService {


    void uploadFile(File file);

    void uploadFile(byte[] bytes, String name);


    void upload(byte[] bytes, String filename, String path);


    String[] listFile(String path);

    List<FileInfo> listALLFile(String path);


    File downloadFile(String fileName, String savePath);


    InputStream readFile(String fileName);


    boolean existFile(String filePath);


    boolean mkdir(String dirName);

    boolean deleteFile(String fileName);


    void uploadFiles(List<MultipartFile> files, boolean deleteSource) throws IOException;


    void uploadFiles(List<MultipartFile> files) throws IOException;


    void uploadFile(MultipartFile multipartFile) throws IOException;



    String listFileNames(String dir);


    File getFile(String dir);


    List<File> mgetFile(String dir);


    boolean rmFile(String file);


    boolean mv(String sourceFile, String targetFile);


    File putFile(String dir);


    List<File> mputFile(String dir);


    //void upload(File file);

    //void upload(byte[] inputStream, String name);

    //List<File> downloadFiles(String dir);


    String nlstFile(String dir);
}
