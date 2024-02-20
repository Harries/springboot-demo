package demo.et59.rocketmq;


import demo.et59.rocketmq.entity.Person;
import demo.et59.rocketmq.util.RocketMqHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MQTests {
	@Autowired
	private RocketMqHelper rocketMqHelper;

	@Test
	public void testProducter() throws InterruptedException {
		for(int i=0;i<1000000;i++){
			Person person = new Person();
			person.setName("heyuhua");
			person.setAge(25);
			rocketMqHelper.asyncSend("PERSON_ADD", MessageBuilder.withPayload(person).build());
			Thread.sleep(1000);
		}
	}

}
