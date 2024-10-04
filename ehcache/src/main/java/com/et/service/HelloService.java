package com.et.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ClassName Hello
 * @Description TODO
 * @Author liuhaihua
 * @Date 2024/10/4 20:24
 * @Version 1.0
 */
@Service
public class HelloService {

	@Cacheable(value = "customcache")
	public  String  sayhi(String name){
		System.out.println("name:"+name);
		return "hi,"+name;
	}
}
