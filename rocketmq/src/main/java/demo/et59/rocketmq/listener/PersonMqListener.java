package demo.et59.rocketmq.listener;

import demo.et59.rocketmq.entity.Person;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: heyuhua
 * @Date: 2021/1/8 15:55
 */
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "PERSON_ADD")
public class PersonMqListener implements RocketMQListener<Person> {
    @Override
    public void onMessage(Person person) {
        System.out.println("接收到消息，开始消费..name:" + person.getName() + ",age:" + person.getAge());
    }
}

