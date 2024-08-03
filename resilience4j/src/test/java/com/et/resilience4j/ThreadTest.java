package com.et.resilience4j;

import org.springframework.web.client.RestTemplate;

public class ThreadTest {
    public static void main(String[] args) {
        for(int i=0;i<6;i++){
            new Thread(()->{
                System.out.println(new RestTemplate().getForObject("http://localhost:8080/hello",String.class));
            }).start();
        }
		/*for(int i=0;i<11;i++){
			new Thread(()->{
				System.out.println(new RestTemplate().getForObject("http://localhost:8080/bulkhead",String.class));
			}).start();
		}*/
    }
}
