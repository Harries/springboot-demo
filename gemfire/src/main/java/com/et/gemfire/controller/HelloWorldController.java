package com.et.gemfire.controller;

import com.et.gemfire.entity.People;
import com.et.gemfire.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Object findById(String id) {
        return personRepository.findById(id);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Object findAll() {
        return personRepository.findAll();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(@RequestBody People bean) {
        personRepository.save(bean);
        return "add OK";
    }
}