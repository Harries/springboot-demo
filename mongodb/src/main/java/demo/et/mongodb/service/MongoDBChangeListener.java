package demo.et.mongodb.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MongoDBChangeListener {

    @KafkaListener(topics = "schema-changes.mongo", groupId = "your-group-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        // 在这里处理接收到的MongoDB数据变化事件
    }
}