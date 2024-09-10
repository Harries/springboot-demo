package com.et.mockito;

import com.et.mockito.controller.HelloWorldController;
import com.et.mockito.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class DemoTests {
    @Autowired
    HelloWorldController controller;
    MockMvc mockMvc;

    @Before
    public void setup() {
        //init mvn env
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @MockBean
    private HelloService helloService;
    @Test
    public void mock1()  {
        //Mockito.when(helloService.sayhi(Mockito.anyString())).thenReturn("harries");
        when(helloService.sayhi("hblog")).thenReturn("harries");
        String  str = helloService.sayhi("hblog");
        log.info(str);
        Assert.assertNotNull(str);


    }
    @Test
    public void mock2()  {
        when(helloService.sayhi("exception")).thenThrow(new RuntimeException("mock throw exception"));
        String  str1 = helloService.sayhi("exception");

    }

    @Test
    public void mock3()  {
        helloService.sayhi("hblog");
        Mockito.verify(helloService, Mockito.times(1)).sayhi(Mockito.eq("hblog")) ;


    }
    @Test
    public void mockController() throws Exception {
        // mock service
        when(helloService.sayhi("hblog")).thenReturn("harries");

        //mock controller
        ResultActions perform = this.mockMvc.perform(get("/hello"));

       log.info(perform.andReturn().getResponse().getContentAsString());
        //verify  responsible
        perform.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{msg:harries}"));  // You don't need to write it in escaped json format
    }



}

