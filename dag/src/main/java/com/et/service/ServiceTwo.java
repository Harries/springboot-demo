package com.et.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName ServiceOne
 * @Description todo
 * @date 2024/09/20/ 14:01
 */
@Service
public class ServiceTwo {
    @Autowired
    ServiceThree serviceThree;
    private   void  sayhi(){
        System.out.println("this is service two sayhi");
    }
}
