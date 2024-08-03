package com.et.resilience4j.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HelloWorldController {
    @RequestMapping("/hello")
	@RateLimiter(name="ratelimitApi",fallbackMethod = "fallback")
	public ResponseEntity<String> showHelloWorld(){
		return new ResponseEntity<>("success",HttpStatus.OK);
    }
	public ResponseEntity fallback(Throwable e){
		log.error("fallback exception , {}",e.getMessage());
		return new ResponseEntity<>("your request is too fast,please low down", HttpStatus.OK);
	}
	int i=0;
	@RequestMapping("/retry")
	@Retry(name = "backendA")//use backendA ，if throw IOException ,it will be retried 3 times。
	public ResponseEntity<String> retry(String name){
		if(name.equals("test")){
			i++;
			log.info("retry time:{}",i);
			throw  new HttpServerErrorException(HttpStatusCode.valueOf(101));
		}
		return new ResponseEntity<>("retry",HttpStatus.OK);
	}


	@RequestMapping("/bulkhead")
	@Bulkhead(name = "backendA")
	public ResponseEntity<String> bulkhead(){

		return new ResponseEntity<>("bulkhead",HttpStatus.OK);
	}
}