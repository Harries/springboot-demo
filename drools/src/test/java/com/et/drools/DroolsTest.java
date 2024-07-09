package com.et.drools;

import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration-level testing for Drools rules.
 */
@SpringBootTest
public class DroolsTest {

    @Autowired
    private KieSession kieSession;

    @Test
    public void shouldFireRuleWithVisa() {
        // given
        Order order = new Order();
        order.setCardType("VISA");
        order.setPrice(14000);
        kieSession.insert(order);

        // when
        kieSession.fireAllRules();

        // then
        assertEquals(14, order.getDiscount());
    }

    @Test
    public void shouldFireRuleWithMasterCard() {
        // given
        Order order = new Order();
        order.setCardType("MASTERCARD");
        order.setPrice(14000);
        kieSession.insert(order);

        // when
        kieSession.fireAllRules();

        // then
        assertEquals(10, order.getDiscount());
    }

    @Test
    public void shouldFireRuleWithICICI() {
        // given
        Order order = new Order();
        order.setCardType("ICICI");
        order.setPrice(3001);
        kieSession.insert(order);

        // when
        kieSession.fireAllRules();

        // then
        assertEquals(20, order.getDiscount());
    }
}
