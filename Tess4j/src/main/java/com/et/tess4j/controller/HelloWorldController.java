package com.et.tess4j.controller;

import com.et.tess4j.service.OcrService;
import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor

public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
	private final OcrService ocrService;

	@PostMapping(value = "/recognize", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String recognizeImage(@RequestParam("file") MultipartFile file) throws TesseractException, IOException {

		return ocrService.recognizeText(file);
	}
}