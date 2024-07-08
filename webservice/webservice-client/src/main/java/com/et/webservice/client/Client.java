package com.et.webservice.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 客户端调用类，用于通过JAX-WS代理方式访问HelloService Web服务。
 */
public class Client {
   

    /**
     * 程序主入口方法。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
   
        // 创建JAX-WS代理工厂对象
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();

        // 设置要访问的服务地址
        jaxWsProxyFactoryBean.setAddress("http://localhost:8088/services/1?wsdl");

        // 设置服务接口类，即HelloService
        jaxWsProxyFactoryBean.setServiceClass(HelloService.class);

        // 使用工厂对象创建HelloService接口的代理实例
        HelloService helloService = jaxWsProxyFactoryBean.create(HelloService.class);

        System.out.println(helloService.getClass());

        // 调用代理实例的方法，向服务端发送请求，并打印返回结果
        System.out.println(helloService.sayHello("hello world"));
    }
}