package com.et.service;

import com.et.bean.Chunk;
import com.et.bean.ChunkProcess;
import org.springframework.core.io.Resource;


public interface FileClient {
    /**
     * 初始化文件客户端
     */
    void initFileClient();


    /**
     * 初始化任务
     *
     * @param filename 文件名称
     * @return 任务id
     */
    String initTask(String filename);

    /**
     * 上传文件块
     *
     * @param chunk 文件块
     * @param uploadId 任务ID
     * @return 对于S3返回eTag地址  对于本地文件返回文件块地址
     */
    String chunk(Chunk chunk, String uploadId);

    /**
     * 文件合并
     *
     * @param chunkProcess 分片详情
     */
    void merge(ChunkProcess chunkProcess);

    /**
     * 根据文件名称获取文件
     *
     * @param filename 文件名称
     * @return 文件资源
     */
    Resource getFile(String filename);
}
