package demo.et.redis;
import demo.et59.redis.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RedisTests {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void save() {
        redisTemplate.opsForValue().set("test","this is a test");
        System.out.println(redisTemplate.opsForValue().get("test"));
    }


}

