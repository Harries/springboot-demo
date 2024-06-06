package com.et.tika.controller;

import com.et.tika.convertor.WordToHtmlConverter;
import com.et.tika.dto.ConvertedDocumentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
    @Autowired
    WordToHtmlConverter converter;



    /**
     * Transforms the Word document into HTML document and returns the transformed document.
     *
     * @return  The content of the uploaded document as HTML.
     */
    @RequestMapping(value = "/api/word-to-html", method = RequestMethod.POST)
    public ConvertedDocumentDTO convertWordDocumentIntoHtmlDocument(@RequestParam(value = "file", required = true) MultipartFile wordDocument) {
        log.info("Converting word document into HTML document");

        ConvertedDocumentDTO htmlDocument = converter.convertWordDocumentIntoHtml(wordDocument);

        log.info("Converted word document into HTML document.");
        log.trace("The created HTML markup looks as follows: {}", htmlDocument);

        return htmlDocument;
    }
}