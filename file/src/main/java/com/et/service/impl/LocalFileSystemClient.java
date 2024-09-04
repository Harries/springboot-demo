package com.et.service.impl;

import cn.hutool.core.io.FileUtil;
import com.et.bean.Chunk;
import com.et.bean.ChunkProcess;
import com.et.service.FileClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class LocalFileSystemClient implements FileClient {
    @Value("${upload.path:/data/upload/}")
    private String filePath;

    public LocalFileSystemClient() {
        // 初始化
        this.initFileClient();
    }

    @Override
    public void initFileClient() {
        FileUtil.mkdir(filePath);
    }

    @Override
    public String initTask(String filename) {
        // 分块文件存储路径
        String tempFilePath = filePath + filename + UUID.randomUUID();
        FileUtil.mkdir(tempFilePath);
        return tempFilePath;
    }


    @Override
    public String chunk(Chunk chunk, String uploadId) {
        String filename = chunk.getFilename();
        String chunkFilePath = uploadId + "/" + chunk.getChunkNumber();
        try (InputStream in = chunk.getFile().getInputStream();
             OutputStream out = Files.newOutputStream(Paths.get(chunkFilePath))) {
            FileCopyUtils.copy(in, out);
        } catch (IOException e) {
            log.error("File [{}] upload failed", filename, e);
            throw new RuntimeException(e);
        }
        return chunkFilePath;
    }

    @Override
    public void merge(ChunkProcess chunkProcess) {
        String filename = chunkProcess.getFilename();
        // 需要合并的文件所在的文件夹
        File chunkFolder = new File(chunkProcess.getUploadId());
        // 合并后的文件
        File mergeFile = new File(filePath + filename);

        try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(mergeFile.toPath()))) {
            byte[] bytes = new byte[1024];
            File[] fileArray = Optional.ofNullable(chunkFolder.listFiles())
                    .orElseThrow(() -> new IllegalArgumentException("Folder is empty"));
            List<File> fileList = Arrays.stream(fileArray).sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
            fileList.forEach(file -> {
                try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
                    int len;
                    while ((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, len);
                    }
                } catch (IOException e) {
                    log.info("File [{}] chunk [{}] write failed", filename, file.getName(), e);
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            log.info("File [{}] merge failed", filename, e);
            throw new RuntimeException(e);
        } finally {
            FileUtil.del(chunkProcess.getUploadId());
        }
    }

    @Override
    public Resource getFile(String filename) {
        File file = new File(filePath + filename);
        return new FileSystemResource(file);
    }
}
