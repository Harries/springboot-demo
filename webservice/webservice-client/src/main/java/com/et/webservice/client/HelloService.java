package com.et.webservice.client;

import javax.jws.WebService;

@WebService(
		name = "MyWebService",
		targetNamespace = "http://liuhaihua.cn/mywebservice"
)
public interface HelloService {
    // 接口名一样

    String sayHello(String name); // 方法定义名一样
}