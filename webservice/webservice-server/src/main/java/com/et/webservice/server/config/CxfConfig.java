package com.et.webservice.server.config;

import com.et.webservice.server.service.MyWebService;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * CXF配置类，负责初始化CXF相关组件、发布Webservice服务以及配置CXF Servlet。
 */
@Configuration
public class CxfConfig {
   

    /**
     * 自动注入Spring Bus实例，它是CXF的核心组件之一，用于管理和配置CXF运行时环境。
     */
    @Autowired
    private SpringBus bus;

    /**
     * 自动注入实现了MyWebService接口的服务实现类实例，该实例将被发布为Webservice供外部调用。
     */
    @Autowired
    private MyWebService myWebServiceImpl;

    /**
     * 创建并返回Webservice端点（Endpoint）实例，用于发布MyWebService服务。
     * 将服务实现类与Spring Bus关联，并指定发布地址为"/1"。
     *
     * @return Webservice端点实例
     */
    @Bean
    public EndpointImpl endpoint() {
   

        EndpointImpl endpoint = new EndpointImpl(bus, myWebServiceImpl);
        endpoint.publish("/1"); // 发布地址
        return endpoint;
    }

    /**
     * 创建并返回CXF Servlet的ServletRegistrationBean实例，用于注册CXF Servlet到Spring Boot的Servlet容器中。
     * 设置CXF Servlet的映射路径为"/services/*"，表示所有以"/services/"开头的HTTP请求都将由CXF Servlet处理。
     *
     * @return CXF Servlet的ServletRegistrationBean实例
     */
    @Bean
    public ServletRegistrationBean wsServlet() {
   
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }
}