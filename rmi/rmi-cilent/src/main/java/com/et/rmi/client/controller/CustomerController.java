package com.et.rmi.client.controller;


import om.et.rmi.common.CustomerDTO;
import om.et.rmi.common.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "customers")
public class CustomerController {

    private RmiProxyFactoryBean proxyFactoryBean;

    public CustomerController(RmiProxyFactoryBean proxyFactoryBean) {
        this.proxyFactoryBean = proxyFactoryBean;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable long id) {
        CustomerService service = (CustomerService) proxyFactoryBean.getObject();
        CustomerDTO dto = service.getCustomer(id);
        return ResponseEntity.ok(dto);
    }
}

