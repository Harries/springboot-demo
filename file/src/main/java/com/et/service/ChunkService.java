package com.et.service;

import com.et.bean.Chunk;
import com.et.bean.FileInfo;

import org.springframework.core.io.Resource;

import java.util.List;


public interface ChunkService {

    void chunk(Chunk chunk);
    void merge(String filename);
    List<FileInfo> list();
    Resource getFile(String filename);
}
