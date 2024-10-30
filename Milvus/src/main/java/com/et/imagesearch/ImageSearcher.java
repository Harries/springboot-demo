package com.et.imagesearch;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.SearchResults;
import io.milvus.param.ConnectParam;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.dml.SearchParam;
import io.milvus.response.SearchResultsWrapper;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ImageSearcher {
	private  MilvusServiceClient milvusClient;

	public ImageSearcher() {
		// 连接到Milvus
		milvusClient = new MilvusServiceClient(
				ConnectParam.newBuilder()
						.withUri("https://in03-xxx.serverless.gcp-us-west1.cloud.zilliz.com")
						.withToken("xx")
						.build());
	}

	public void search(INDArray queryFeatures) {
		// 在Milvus中进行相似性搜索
		float[] floatArray = queryFeatures.toFloatVector();
		List<Float> floatList = new ArrayList<>();
		for (float f : floatArray) {
			floatList.add(f);
		}
		List<List<Float>> vectors = Collections.singletonList(floatList);


		SearchParam searchParam = SearchParam.newBuilder()
				.withCollectionName("image_collection")
				.withMetricType(MetricType.L2)
				.withTopK(5)
				.withVectors(vectors)
				.withVectorFieldName("embedding")
				.build();

		R<SearchResults> searchResults = milvusClient.search(searchParam);


		System.out.println("Searching vector: " + queryFeatures.toFloatVector());
		System.out.println("Result: " + searchResults.getData().getResults().getFieldsDataList());
	}
}
