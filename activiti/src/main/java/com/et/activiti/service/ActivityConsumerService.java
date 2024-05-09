package com.et.activiti.service;

import org.activiti.engine.task.TaskQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 

public interface ActivityConsumerService {

 public boolean startActivityDemo();
 
}