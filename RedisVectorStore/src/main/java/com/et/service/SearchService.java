package com.et.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {


	@Value("${topk:10}")
	private int topK;

	@Autowired
	private  VectorStore vectorStore;



	public List<Document> retrieve(String message) {
		SearchRequest request = SearchRequest.query(message).withTopK(topK);
		// Query Redis for the top K documents most relevant to the input message
		List<Document> docs = vectorStore.similaritySearch(request);

		return docs;
	}

}
