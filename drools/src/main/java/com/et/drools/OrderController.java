package com.et.drools;

import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final KieSession kieSession;

    public OrderController(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    @PostMapping("/order")
    public Order processRules(@RequestBody Order order) {
        kieSession.insert(order);
        kieSession.fireAllRules();
        return order;
    }
}
