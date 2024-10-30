package com.et.imagesearch;

import io.milvus.client.*;
import io.milvus.param.*;
import io.milvus.param.collection.*;
import io.milvus.param.dml.*;
import io.milvus.grpc.*;
import io.milvus.param.index.CreateIndexParam;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MilvusManager {
    private  MilvusServiceClient milvusClient;

    public MilvusManager() {
		// 连接到Milvus
		milvusClient = new MilvusServiceClient(
				ConnectParam.newBuilder()
						.withUri("https://in03-xxx.serverless.gcp-us-west1.cloud.zilliz.com")
						.withToken("xxx")
						.build());
    }

    public void createCollection() {
        // 定义集合和字段
        FieldType idField = FieldType.newBuilder()
                .withName("id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .build();

        FieldType vectorField = FieldType.newBuilder()
                .withName("embedding")
                .withDataType(DataType.FloatVector)
                .withDimension(1000)
                .build();

        CreateCollectionParam createCollectionParam = CreateCollectionParam.newBuilder()
                .withCollectionName("image_collection")
                .withDescription("Image collection")
                .withShardsNum(2)
                .addFieldType(idField)
                .addFieldType(vectorField)
                .build();

        milvusClient.createCollection(createCollectionParam);
    }

    public void insertData(long id, INDArray features) {
        // 插入数据
        List<Long> ids = Collections.singletonList(id);
        float[] floatArray = features.toFloatVector();

        // 手动将 float[] 转换为 List<Float>
        List<Float> floatList = new ArrayList<>();
        for (float f : floatArray) {
            floatList.add(f); // 将每个 float 添加到列表中
        }

        // 创建 List<List<Float>>
        List<List<Float>> vectors = Collections.singletonList(floatList);

        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field("id",ids));
        fields.add(new InsertParam.Field("embedding", vectors));
        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName("image_collection")
                .withFields(fields)
                .build();

        milvusClient.insert(insertParam);

    }
	public void flush() {
		milvusClient.flush(FlushParam.newBuilder()
				.withCollectionNames(Collections.singletonList("image_collection"))
				.withSyncFlush(true)
				.withSyncFlushWaitingInterval(50L)
				.withSyncFlushWaitingTimeout(30L)
				.build());
	}

	public void buildindex() {
		// build index
		System.out.println("Building AutoIndex...");
		final IndexType INDEX_TYPE = IndexType.AUTOINDEX;   // IndexType
		long startIndexTime = System.currentTimeMillis();
		R<RpcStatus> indexR = milvusClient.createIndex(
				CreateIndexParam.newBuilder()
						.withCollectionName("image_collection")
						.withFieldName("embedding")
						.withIndexType(INDEX_TYPE)
						.withMetricType(MetricType.L2)
						.withSyncMode(Boolean.TRUE)
						.withSyncWaitingInterval(500L)
						.withSyncWaitingTimeout(30L)
						.build());
		long endIndexTime = System.currentTimeMillis();
		System.out.println("Succeed in " + (endIndexTime - startIndexTime) / 1000.00 + " seconds!");
	}
}