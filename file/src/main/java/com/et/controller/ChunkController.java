package com.et.controller;

import com.et.bean.Chunk;
import com.et.bean.FileInfo;
import com.et.service.ChunkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("file")
public class ChunkController {
    @Autowired
    private ChunkService chunkService;

    /**
     * upload by part
     *
     * @param chunk
     * @return
     */
    @PostMapping(value = "chunk")
    public ResponseEntity<String> chunk(Chunk chunk) {
        chunkService.chunk(chunk);
        return ResponseEntity.ok("File Chunk Upload Success");
    }

    /**
     * merge
     *
     * @param filename
     * @return
     */
    @GetMapping(value = "merge")
    public ResponseEntity<Void> merge(@RequestParam("filename") String filename) {
        chunkService.merge(filename);
        return ResponseEntity.ok().build();
    }


    /**
     * get fileName
     *
     * @return files
     */
    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> list() {
        return ResponseEntity.ok(chunkService.list());
    }

    /**
     * get single file
     *
     * @param filename
     * @return file
     */
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"").body(chunkService.getFile(filename));
    }



}
