package com.et.imagesearch;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.SearchResults;
import io.milvus.param.ConnectParam;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.dml.SearchParam;
import io.milvus.response.SearchResultsWrapper;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Collections;
import java.util.List;

public class ImageSearcher {
    private  MilvusServiceClient milvusClient;

    public ImageSearcher() {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost("localhost") // 设置Milvus服务器的主机地址
                .withPort(19530)       // 设置Milvus服务器的端口
                .build();

        // 创建Milvus客户端
        milvusClient = new MilvusServiceClient(connectParam);
    }

    public void search(INDArray queryFeatures) {
        // 在Milvus中进行相似性搜索

        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName("image_collection")
                .withMetricType(MetricType.L2)
                .withTopK(5)
                .withVectors(Collections.singletonList(queryFeatures.toFloatVector()))
                .withVectorFieldName("embedding")
                .build();

        R<SearchResults> searchResults = milvusClient.search(searchParam);

        // 输出结果
        System.out.println("Searching vector: " + queryFeatures.toFloatVector());
        System.out.println("Result: " + searchResults.getData().getResults().getFieldsDataList());
    }
}