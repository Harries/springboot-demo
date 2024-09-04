package com.et.controller;

import com.et.bean.FileInfo;
import com.et.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
    @Autowired
    private FileUploadService fileUploadService;


    /**
     * upload
     *
     * @param files
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("files") MultipartFile[] files) {
        fileUploadService.upload(files);
        return ResponseEntity.ok("File Upload Success");
    }

    /**
     *  files
     *
     * @return
     */
    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> list() {
        return ResponseEntity.ok(fileUploadService.list());
    }

    /**
     * get file by name
     *
     * @param fileName
     * @return
     */
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"").body(fileUploadService.getFile(fileName));
    }
}