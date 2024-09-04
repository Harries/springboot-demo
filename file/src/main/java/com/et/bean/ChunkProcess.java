package com.et.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChunkProcess {
    private String uploadId; // s3 对应的分片上传任务的id
    private String filename;
    private List<ChunkPart> chunkList = new ArrayList<>(); // 分片信息


    /**
     * 使用一个类来表示每个分片保存的信息
     * 对于本地文件系统 保存每块分片保存的路径
     * 对于AWS S3记录每个分片的eTag信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChunkPart {
        private String location;
        private int chunkNumber;
    }
}
