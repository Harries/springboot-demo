package com.et.resilience4j.controller;

import com.et.resilience4j.model.Activity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequestMapping("/activity")
@RestController
public class ActivityController {

    private RestTemplate restTemplate;

    private final String BORED_API = "http://www.liuhaihua.cn/api/activity";

    public ActivityController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    @CircuitBreaker(name = "randomActivity", fallbackMethod = "fallbackRandomActivity")
    public String getRandomActivity() {
        ResponseEntity<Activity> responseEntity = restTemplate.getForEntity(BORED_API, Activity.class);
        Activity activity = responseEntity.getBody();
        log.info("Activity received: " + activity.getActivity());
        return activity.getActivity();
    }

    public String fallbackRandomActivity(Throwable throwable) {
        return "fail to read a Activity from HBLOG";
    }

}

