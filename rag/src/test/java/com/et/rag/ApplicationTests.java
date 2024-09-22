package com.et.rag;

import com.et.rag.service.SBotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private SBotService sBotService;

    @Test
    void testQuestions() {
        List<String> questions = List.of(
                "How to create a Spring Boot application with spring data jpa query methods?",
                "What is the difference between Webflux and Web MVC in Spring Boot?",
                "How to use docker compose with Spring Boot?"
        );

        for (String question : questions) {
            String answer = sBotService.askQuestion(question);
            System.out.println("Question: " + question);
            System.out.println("Answer: " + answer);
            System.out.println("======================================================");
        }
    }
}
