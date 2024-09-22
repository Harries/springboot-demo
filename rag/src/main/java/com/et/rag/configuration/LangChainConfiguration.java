package com.et.rag.configuration;

import com.et.rag.retriever.EmbeddingStoreLoggingRetriever;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

import static com.et.rag.constant.Constants.PROMPT_TEMPLATE_2;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class LangChainConfiguration {

    @Value("${langchain.api.key}")
    private String apiKey;

    @Value("${langchain.timeout}")
    private Long timeout;

    private final List<Document> documents;

    @Bean
    public ConversationalRetrievalChain chain() {

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(500, 0))
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        log.info("Ingesting Spring Boot Resources ...");

        ingestor.ingest(documents);

        log.info("Ingested {} documents", documents.size());

        EmbeddingStoreRetriever retriever = EmbeddingStoreRetriever.from(embeddingStore, embeddingModel);
        EmbeddingStoreLoggingRetriever loggingRetriever = new EmbeddingStoreLoggingRetriever(retriever);

        /*MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();*/

        log.info("Building ConversationalRetrievalChain ...");

        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
                .chatLanguageModel(OpenAiChatModel.builder()
                        .apiKey(apiKey)
                        .timeout(Duration.ofSeconds(timeout))
                        .build()
                )
                .promptTemplate(PromptTemplate.from(PROMPT_TEMPLATE_2))
                //.chatMemory(chatMemory)
                .retriever(loggingRetriever)
                .build();

        log.info("Spring Boot knowledge base is ready!");
        return chain;
    }
}
