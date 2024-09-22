package com.et.rag.service;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SBotService {

    private final ConversationalRetrievalChain chain;

    public String askQuestion(String question) {
        log.debug("======================================================");
        log.debug("Question: " + question);
        String answer = chain.execute(question);
        log.debug("Answer: " + answer);
        log.debug("======================================================");
        return answer;
    }
}
