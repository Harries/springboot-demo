package com.et;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());


    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute()  {
        log.info("your method test Code");
        //  Redisson client
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");

        // create Redisson client instant
        RedissonClient redisson = Redisson.create(config);

        // create RBloomFilter
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("myBloomFilter");

        // Initialize the Bloom filter, set the expected number of elements to be inserted and the false positive rate
        bloomFilter.tryInit(10000L, 0.03);

        // inert element
        bloomFilter.add("key1");
        bloomFilter.add("key2");

        // find element
        boolean mightContainYi = bloomFilter.contains("key3");
        System.out.println("bloomFilter may contain 'key1'：" + mightContainYi);

        // close Redisson client
        redisson.shutdown();
    }

}

