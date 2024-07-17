package com.et.sftp.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {
    private String host;
    private Integer port;
    private String password;
    private String username;
    private String remoteDir;
    private String localDir;
}
