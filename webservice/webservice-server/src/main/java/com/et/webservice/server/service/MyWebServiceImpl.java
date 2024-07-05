package com.et.webservice.server.service;


import org.springframework.stereotype.Service;

import javax.jws.WebService;


@Service
@WebService
public class MyWebServiceImpl implements MyWebService {
   

    @Override
    public String sayHello(String name) {
   
        System.err.println("sayHello is called..."); // 只是为了更明显的输出，采用err

        return "Hello, " + name + "!";
    }
}