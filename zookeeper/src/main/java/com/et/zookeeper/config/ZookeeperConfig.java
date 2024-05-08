package com.et.zookeeper.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Configuration
@Slf4j
public class ZookeeperConfig {

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int timeout;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //After the connection is successful, the watcher will be called back to monitor. This connection operation is asynchronous. After the new statement is executed, the subsequent code will be called directly.
            // Multiple service addresses can be specified: 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(connectString, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        //If the response event from the server is received, the connection is successful
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            log.info("【init zooKeeper connect....】={}", zooKeeper.getState());

        } catch (Exception e) {
            log.error("init ZooKeeper connect error....】={}", e);
        }
        return zooKeeper;
    }
}