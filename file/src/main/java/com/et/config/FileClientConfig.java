package com.et.config;

import com.et.service.FileClient;
import com.et.service.impl.LocalFileSystemClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


@Configuration
public class FileClientConfig {
    @Value("${file.client.type:local-file}")
    private String fileClientType;

    private static final Map<String, Supplier<FileClient>> FILE_CLIENT_SUPPLY = new HashMap<String, Supplier<FileClient>>() {
        {
            put("local-file", LocalFileSystemClient::new);
           // put("aws-s3", AWSFileClient::new);
        }
    };

    /**
     * get client
     *
     * @return
     */
    @Bean
    public FileClient fileClient() {
        return FILE_CLIENT_SUPPLY.get(fileClientType).get();
    }
}
