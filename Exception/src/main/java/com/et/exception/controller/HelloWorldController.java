package com.et.exception.controller;

import com.et.exception.config.BizException;
import com.et.exception.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public Map<String, Object> showHelloWorld(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "HelloWorld");
        return map;
    }
	@PostMapping("/user")
	public boolean insert(@RequestBody User user) {
		System.out.println("add...");
		if(user.getName()==null){
			throw  new BizException("-1","username is empty！");
		}
		return true;
	}

	@PutMapping("/user")
	public boolean update(@RequestBody User user) {
		System.out.println("update...");
		//mock NullException
		String str=null;
		str.equals("111");
		return true;
	}

	@DeleteMapping("/user")
	public boolean delete(@RequestBody User user)  {
		System.out.println("delete...");
		//mock Exception
		Integer.parseInt("abc123");
		return true;
	}


}