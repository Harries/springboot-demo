package com.et;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		RedissonClient redisson = context.getBean(RedissonClient.class);
		RBloomFilter bf = redisson.getBloomFilter("test-bloom-filter");
		bf.tryInit(100000L, 0.03);
		Set<String> set = new HashSet<String>(1000);
		List<String> list = new ArrayList<String>(1000);
		//Fill the Bloom filter with data. To test the reality, we recorded 1000 uuids and another 9000 as interference data.
		for (int i = 0; i < 10000; i++) {
			String uuid = UUID.randomUUID().toString();
			if(i<1000){
				set.add(uuid);
				list.add(uuid);
			}

			bf.add(uuid);
		}

		int wrong = 0; // Number of false positives by the Bloom filter
		int right = 0;// Bloom filter correct times
		for (int i = 0; i < 10000; i++) {
			String str = i % 10 == 0 ? list.get(i / 10) : UUID.randomUUID().toString();
			System.out.println(str);
			if (bf.contains(str)) {
				if (set.contains(str)) {
					right++;
				} else {
					wrong++;
				}
			}
		}

		//right is 1000
		System.out.println("right:" + right);
		//Because the error rate is 3%, the wrong value of 10,000 data is about 30.
		System.out.println("wrong:" + wrong);
		//Filter remaining space size
		System.out.println(bf.count());
	}
}
