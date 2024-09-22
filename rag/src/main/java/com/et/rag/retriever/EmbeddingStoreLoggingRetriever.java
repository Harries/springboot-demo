package com.et.rag.retriever;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * EmbeddingStoreLoggingRetriever is a logging-enhanced for an EmbeddingStoreRetriever.
 * <p>
 * This class logs the relevant TextSegments discovered by the supplied
 * EmbeddingStoreRetriever for improved transparency and debugging.
 * <p>
 * Logging happens at INFO level, printing each relevant TextSegment found
 * for a given input text once the findRelevant method is called.
 */

@RequiredArgsConstructor
@Slf4j
public class EmbeddingStoreLoggingRetriever implements Retriever<TextSegment> {

    private final EmbeddingStoreRetriever retriever;

    @Override
    public List<TextSegment> findRelevant(String text) {
        List<TextSegment> relevant = retriever.findRelevant(text);
        relevant.forEach(segment -> {
            log.debug("=======================================================");
            log.debug("Found relevant text segment: {}", segment);
        });
        return relevant;
    }
}
