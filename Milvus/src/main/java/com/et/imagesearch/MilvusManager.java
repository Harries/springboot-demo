package com.et.imagesearch;

import io.milvus.client.*;
import io.milvus.param.collection.*;
import io.milvus.param.dml.*;
import io.milvus.grpc.*;

import java.util.Collections;
import java.util.List;

public class MilvusManager {
    private MilvusClient client;

    public MilvusManager() {
        // 连接到Milvus
        client = new MilvusGrpcClient(MilvusServiceClientConfig.builder().build());
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
                .withDimension(2048)
                .build();

        CreateCollectionParam createCollectionParam = CreateCollectionParam.newBuilder()
                .withCollectionName("image_collection")
                .withDescription("Image collection")
                .withShardsNum(2)
                .addFieldType(idField)
                .addFieldType(vectorField)
                .build();

        client.createCollection(createCollectionParam);
    }

    public void insertData(long id, INDArray features) {
        // 插入数据
        List<Long> ids = Collections.singletonList(id);
        List<List<Float>> vectors = Collections.singletonList(features.toFloatVector());

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName("image_collection")
                .addField("id", ids)
                .addField("embedding", vectors)
                .build();

        client.insert(insertParam);
    }
}