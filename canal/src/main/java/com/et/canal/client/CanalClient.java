package com.et.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;

@Component
public class CanalClient {

    @Autowired
    private CanalConnector canalConnector;

    @PostConstruct
    public void listen() {
        Executors.newSingleThreadExecutor().submit(() -> {
            canalConnector.connect();
            canalConnector.subscribe(".*\\..*");
            canalConnector.rollback();
            try {
                while (true) {
                    Message message = canalConnector.getWithoutAck(1000);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId != -1 && size > 0) {
                        handleEntries(message.getEntries());
                    }
                    canalConnector.ack(batchId); // 提交确认
                }
            } finally {
                canalConnector.disconnect();
            }
        });
    }

    private void handleEntries(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            // 根据实际业务处理逻辑
        }
    }
}
