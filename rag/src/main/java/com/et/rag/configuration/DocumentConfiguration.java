package com.et.rag.configuration;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.UrlDocumentLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.et.rag.constant.Constants.SPRING_BOOT_RESOURCES_LIST;


@Configuration
public class DocumentConfiguration {
    @Bean
    public List<Document> documents() {
        return SPRING_BOOT_RESOURCES_LIST.stream()
                .map(url -> {
                    try {
                        return UrlDocumentLoader.load(url);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to load document from " + url, e);
                    }
                })
                .toList();
    }
}
