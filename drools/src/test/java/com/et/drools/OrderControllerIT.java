package com.et.drools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration-level testing for {@link OrderController} object.
 * Show the result of the Drools engine.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    public static final String VISA = "VISA";
    public static final String MASTER_CARD = "MASTERCARD";
    public static final String ICICI = "ICICI";

    @Test
    public void shouldApplyVISARule() throws Exception {
        //given
        Order order = new Order();
        order.setCardType(VISA);
        order.setPrice(11000);

        //when
        MockHttpServletRequestBuilder request = createPostOrder(order);
        String contentAsString = performAction(request);
        Order resultOrder = objectMapper.readValue(contentAsString, Order.class);

        //then
        assertEquals(order.getCardType(), resultOrder.getCardType());
        assertEquals(order.getPrice(), resultOrder.getPrice());
        assertEquals(14, resultOrder.getDiscount());
    }

    @Test
    public void shouldApplyMASTERCARDRule() throws Exception {
        //given
        Order order = new Order();
        order.setCardType(MASTER_CARD);
        order.setPrice(11000);

        //when
        MockHttpServletRequestBuilder request = createPostOrder(order);
        String contentAsString = performAction(request);
        Order resultOrder = objectMapper.readValue(contentAsString, Order.class);

        //then
        assertEquals(order.getCardType(), resultOrder.getCardType());
        assertEquals(order.getPrice(), resultOrder.getPrice());
        assertEquals(10, resultOrder.getDiscount());
    }

    @Test
    public void shouldApplyICICIRule() throws Exception {
        //given
        Order order = new Order();
        order.setCardType(ICICI);
        order.setPrice(11000);

        //when
        MockHttpServletRequestBuilder request = createPostOrder(order);
        String contentAsString = performAction(request);
        Order resultOrder = objectMapper.readValue(contentAsString, Order.class);

        //then
        assertEquals(order.getCardType(), resultOrder.getCardType());
        assertEquals(order.getPrice(), resultOrder.getPrice());
        assertEquals(20, resultOrder.getDiscount());
    }

    private MockHttpServletRequestBuilder createPostOrder(Order order) throws JsonProcessingException {
        return post("/order")
                .content(objectMapper.writeValueAsBytes(order))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);

    }

    private String performAction(MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
