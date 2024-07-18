package com.et.sftp.service.impl;

import com.et.sftp.config.SftpConfiguration;
import com.et.sftp.service.SftpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author : qiushicai
 * @date : Created in 2020/11/20
 * @description :
 */
@Slf4j
@Component
public class SftpServiceImpl implements SftpService {

    @Resource
    private SftpRemoteFileTemplate remoteFileTemplate;

    @Resource
    private SftpConfiguration.SftpGateway gateway;

    /**
     * 单文件上传
     *
     * @param file File
     */
    @Override
    public void uploadFile(File file) {
        gateway.upload(file);
    }

    /**
     * 单文件上传 byte字节流直接上传
     *
     * @param bytes bytes
     */
    @Override
    public void uploadFile(byte[] bytes, String name) {
        try {
            gateway.upload(bytes, name);
        } catch (Exception e) {
            log.error("error:", e);
        }
    }

    /**
     * 指定上传路劲
     *
     * @param bytes
     * @param filename
     * @param path
     */
    @Override
    public void upload(byte[] bytes, String filename, String path) {
        try {
            gateway.upload(bytes, filename, path);
        } catch (Exception e) {
            log.error("error:", e);
        }
    }

    /**
     * 查询某个路径下所有文件
     *
     * @param path
     * @return List<String>
     */
    @Override
    public String[] listFile(String path) {
        try {
            return remoteFileTemplate.execute(session -> {
                return session.listNames(path);
            });
        } catch (Exception e) {
            log.error("error:", e);
        }
        return null;
    }


    /**
     * 查询某个路径下所有文件包括文件夹
     *
     * @param path
     * @return List<String>
     */
    @Override
    public List<FileInfo> listALLFile(String path) {
        return gateway.listFile(path);
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param savePath 本地文件存储位置
     * @return File
     */
    @Override
    public File downloadFile(String fileName, String savePath) {
        try {
            return remoteFileTemplate.execute(session -> {
                remoteFileTemplate.setAutoCreateDirectory(true);
                boolean existFile = session.exists(fileName);
                if (existFile) {
                    InputStream is = session.readRaw(fileName);
                    return convertInputStreamToFile(is, savePath);
                } else {
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("error:", e);
        }
        return null;
    }

    /**
     * 读取文件
     *
     * @param fileName
     * @return InputStream
     */

    @Override
    public InputStream readFile(String fileName) {
        return remoteFileTemplate.execute(session -> {
            return session.readRaw(fileName);
        });
    }

    /**
     * 文件是否存在
     *
     * @param filePath 文件名
     * @return boolean
     */
    @Override
    public boolean existFile(String filePath) {
        try {
            return remoteFileTemplate.execute(session ->
                    session.exists(filePath));
        } catch (Exception e) {
            log.error("error:", e);
        }
        return false;
    }

    public void renameFile(String file1, String file2) {
        try {
            remoteFileTemplate.execute(session -> {
                session.rename(file1, file2);
                return true;
            });
        } catch (Exception e) {
            log.error("error:", e);
        }
    }

    /**
     * 创建文件夹
     *
     * @param dirName
     * @return
     */

    @Override
    public boolean mkdir(String dirName) {
        return remoteFileTemplate.execute(session -> {
            if (!existFile(dirName)) {

                return session.mkdir(dirName);
            } else {
                return false;
            }
        });
    }

    /**
     * 删除文件
     *
     * @param fileName 待删除文件名
     * @return boolean
     */
    @Override
    public boolean deleteFile(String fileName) {
        return remoteFileTemplate.execute(session -> {
            boolean existFile = session.exists(fileName);
            if (existFile) {
                return session.remove(fileName);
            } else {
                log.info("file : {} not exist", fileName);
                return false;
            }
        });
    }

    /**
     * 批量上传 (MultipartFile)
     *
     * @param files List<MultipartFile>
     * @throws IOException
     */
    @Override
    public void uploadFiles(List<MultipartFile> files, boolean deleteSource) throws IOException {
        try {
            for (MultipartFile multipartFile : files) {
                if (multipartFile.isEmpty()) {
                    continue;
                }
                File file = convert(multipartFile);
                gateway.upload(file);
                if (deleteSource) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            log.error("error:", e);
        }
    }

    /**
     * 批量上传 (MultipartFile)
     *
     * @param files List<MultipartFile>
     * @throws IOException
     */
    @Override
    public void uploadFiles(List<MultipartFile> files) throws IOException {
        uploadFiles(files, true);
    }

    /**
     * 单文件上传 (MultipartFile)
     *
     * @param multipartFile MultipartFile
     * @throws IOException
     */
    @Override
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        gateway.upload(convert(multipartFile));
    }

    @Override
    public String listFileNames(String dir) {
        return gateway.nlstFile(dir);
    }

    @Override
    public File getFile(String dir) {
        return null;
    }

    @Override
    public List<File> mgetFile(String dir) {
        return null;
    }

    @Override
    public boolean rmFile(String file) {
        return false;
    }

    @Override
    public boolean mv(String sourceFile, String targetFile) {
        return false;
    }

    @Override
    public File putFile(String dir) {
        return null;
    }

    @Override
    public List<File> mputFile(String dir) {
        return null;
    }

    @Override
    public String nlstFile(String dir) {
        return gateway.nlstFile(dir);
    }

    private static File convertInputStreamToFile(InputStream inputStream, String savePath) {
        OutputStream outputStream = null;
        File file = new File(savePath);
        try {
            outputStream = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            log.info("convert InputStream to file done, savePath is : {}", savePath);
        } catch (IOException e) {
            log.error("error:", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("error:", e);
                }
            }
        }
        return file;
    }

    private static File convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }
}
