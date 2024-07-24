package com.et.canal.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CanalConfig {

    @Value("${canal.host}")
    private String host;

    @Value("${canal.port}")
    private int port;

    @Value("${canal.destination}")
    private String destination;

    @Value("${canal.username}")
    private String username;

    @Value("${canal.password}")
    private String password;

    @Bean
    public CanalConnector canalConnector() {
        // 创建连接器
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(host, port),
                destination, username, password);
        return connector;
    }
}
