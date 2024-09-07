package com.et.service.impl;

import com.et.bean.Chunk;
import com.et.bean.ChunkProcess;
import com.et.bean.FileInfo;
import com.et.service.ChunkService;
import com.et.service.FileClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;




@Service
@Slf4j
public class ChunkServiceImpl implements ChunkService {
    // process
    private static final Map<String, ChunkProcess> CHUNK_PROCESS_STORAGE = new ConcurrentHashMap<>();

    // file list
    private static final List<FileInfo> FILE_STORAGE = new CopyOnWriteArrayList<>();


    @Autowired
    private FileClient fileClient;


    @Override
    public void chunk(Chunk chunk) {
        String filename = chunk.getFilename();
        boolean match = FILE_STORAGE.stream().anyMatch(fileInfo -> fileInfo.getFileName().equals(filename));
        if (match) {
            throw new RuntimeException("File [ " + filename + " ] already exist");
        }
        ChunkProcess chunkProcess;
        String uploadId;
        if (CHUNK_PROCESS_STORAGE.containsKey(filename)) {
            chunkProcess = CHUNK_PROCESS_STORAGE.get(filename);
            uploadId = chunkProcess.getUploadId();
            AtomicBoolean isUploaded = new AtomicBoolean(false);
            Optional.ofNullable(chunkProcess.getChunkList()).ifPresent(chunkPartList ->
                    isUploaded.set(chunkPartList.stream().anyMatch(chunkPart -> chunkPart.getChunkNumber() == chunk.getChunkNumber())));
            if (isUploaded.get()) {
                log.info("file【{}】chunk【{}】upload，jump", chunk.getFilename(), chunk.getChunkNumber());
                return;
            }
        } else {
            uploadId = fileClient.initTask(filename);
            chunkProcess = new ChunkProcess().setFilename(filename).setUploadId(uploadId);
            CHUNK_PROCESS_STORAGE.put(filename, chunkProcess);
        }

        List<ChunkProcess.ChunkPart> chunkList = chunkProcess.getChunkList();
        String chunkId = fileClient.chunk(chunk, uploadId);
        chunkList.add(new ChunkProcess.ChunkPart(chunkId, chunk.getChunkNumber()));
        CHUNK_PROCESS_STORAGE.put(filename, chunkProcess.setChunkList(chunkList));
    }

    @Override
    public void merge(String filename) {
        ChunkProcess chunkProcess = CHUNK_PROCESS_STORAGE.get(filename);
        fileClient.merge(chunkProcess);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());
        FILE_STORAGE.add(new FileInfo().setUploadTime(currentTime).setFileName(filename));
        CHUNK_PROCESS_STORAGE.remove(filename);
    }

    @Override
    public List<FileInfo> list() {
        return FILE_STORAGE;
    }

    @Override
    public Resource getFile(String filename) {
        return fileClient.getFile(filename);
    }
}
