package com.et.protobuf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = DemoApplication.class)
public class ApplicationTest {
    // Other declarations
    private static final String COURSE1_URL = "http://localhost:8088/protobuf/1";

    @Autowired
    private RestTemplate restTemplate ;

    @Test
    public void whenUsingRestTemplate_thenSucceed() {

        ResponseEntity<ProtobufMessage.Student> student = restTemplate.getForEntity(COURSE1_URL, ProtobufMessage.Student.class);
        System.out.println(student.toString());
    }
}
