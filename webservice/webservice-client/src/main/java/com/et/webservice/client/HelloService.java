package com.et.webservice.client;

import jakarta.jws.WebService;

@WebService // Webservice注解表明是一个Webservice的服务类
        (targetNamespace = "http://liuhaihua.cn/mywebservice") // 指定服务的命名空间
public interface HelloService {
    // 接口名一样

    String sayHello(String name); // 方法定义名一样
}