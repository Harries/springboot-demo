package com.et.imagesearch;

import io.milvus.client.*;
import io.milvus.param.ConnectParam;
import io.milvus.param.collection.*;
import io.milvus.param.dml.*;
import io.milvus.grpc.*;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MilvusManager {
    private  MilvusServiceClient milvusClient;

    public MilvusManager() {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost("localhost") // 设置Milvus服务器的主机地址
                .withPort(19530)       // 设置Milvus服务器的端口
                .build();

        // 创建Milvus客户端
        milvusClient = new MilvusServiceClient(connectParam);
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
}