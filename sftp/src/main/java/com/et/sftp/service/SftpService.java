package com.et.sftp.service;

import org.springframework.integration.file.remote.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author : qiushicai
 * @date : Created in 2020/11/20
 * @description :
 */
public interface SftpService {


    /**
     * 单文件上传
     *
     * @param file File
     */
    void uploadFile(File file);

    /**
     * 单文件上传 byte字节流直接上传
     *
     * @param bytes bytes
     */
    void uploadFile(byte[] bytes, String name);

    /**
     * 指定上传路劲
     *
     * @param bytes
     * @param filename
     * @param path
     */
    void upload(byte[] bytes, String filename, String path);

    /**
     * 查询某个路径下所有文件
     *
     * @param path
     * @return List<String>
     */
    String[] listFile(String path);


    /**
     * 查询某个路径下所有文件包括文件夹
     *
     * @param path
     * @return List<String>
     */
    List<FileInfo> listALLFile(String path);

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param savePath 本地文件存储位置
     * @return File
     */
    File downloadFile(String fileName, String savePath);

    /**
     * 读取文件
     *
     * @param fileName
     * @return InputStream
     */

    InputStream readFile(String fileName);

    /**
     * 文件是否存在
     *
     * @param filePath 文件名
     * @return boolean
     */
    boolean existFile(String filePath);

    /**
     * 创建文件夹
     *
     * @param dirName
     * @return
     */

    boolean mkdir(String dirName);

    /**
     * 删除文件
     *
     * @param fileName 待删除文件名
     * @return boolean
     */
    boolean deleteFile(String fileName);

    /**
     * 批量上传 (MultipartFile)
     *
     * @param files List<MultipartFile>
     * @throws IOException
     */
    void uploadFiles(List<MultipartFile> files, boolean deleteSource) throws IOException;

    /**
     * 批量上传 (MultipartFile)
     *
     * @param files List<MultipartFile>
     * @throws IOException
     */
    void uploadFiles(List<MultipartFile> files) throws IOException;

    /**
     * 单文件上传 (MultipartFile)
     *
     * @param multipartFile MultipartFile
     * @throws IOException
     */
    void uploadFile(MultipartFile multipartFile) throws IOException;


    /**
     * 列出指定目录的文件名：outbound gateway -nlst
     *
     * @param dir
     * @return 文件名列表 (","分割)
     */
    String listFileNames(String dir);


    /**
     * outbound gateway-get
     *
     * @param dir
     * @return
     */
    File getFile(String dir);

    /**
     * outbound gateway-mget
     *
     * @param dir
     * @return
     */
    List<File> mgetFile(String dir);

    /**
     * outbound gateway-rm
     *
     * @param file
     * @return
     */
    boolean rmFile(String file);

    /**
     * outbound gateway-mv
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    boolean mv(String sourceFile, String targetFile);

    /**
     * outbound gateway-put
     *
     * @param dir
     * @return
     */
    File putFile(String dir);

    /**
     * outbound gateway-mput
     *
     * @param dir
     * @return
     */
    List<File> mputFile(String dir);


    //void upload(File file);

    //void upload(byte[] inputStream, String name);

    //List<File> downloadFiles(String dir);


    String nlstFile(String dir);
}
