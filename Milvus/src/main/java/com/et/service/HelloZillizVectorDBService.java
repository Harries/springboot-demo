package com.et.service;


import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.grpc.DescribeCollectionResponse;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.param.*;
import io.milvus.param.collection.*;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HelloZillizVectorDBService {
	@Value("${uri}")
	public String uri;
	@Value("${token}")
	public String token;

    public  void search() {
        // connect to milvus
        final MilvusServiceClient milvusClient = new MilvusServiceClient(
                ConnectParam.newBuilder()
                        .withUri(uri)
                        .withToken(token)
                        .build());
        System.out.println("Connecting to DB: " + uri);
        // Check if the collection exists
        String collectionName = "book";
        R<DescribeCollectionResponse> responseR =
                milvusClient.describeCollection(DescribeCollectionParam.newBuilder().withCollectionName(collectionName).build());
        if (responseR.getData() != null) {
            milvusClient.dropCollection(DropCollectionParam.newBuilder().withCollectionName(collectionName).build());
        }
        System.out.println("Success!");
        // create a collection with customized primary field: book_id_field
        int dim = 64;
        FieldType bookIdField = FieldType.newBuilder()
                .withName("book_id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(false)
                .build();
        FieldType wordCountField = FieldType.newBuilder()
                .withName("word_count")
                .withDataType(DataType.Int64)
                .build();
        FieldType bookIntroField = FieldType.newBuilder()
                .withName("book_intro")
                .withDataType(DataType.FloatVector)
                .withDimension(dim)
                .build();
        CreateCollectionParam createCollectionParam = CreateCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .withDescription("my first collection")
                .withShardsNum(2)
                .addFieldType(bookIdField)
                .addFieldType(wordCountField)
                .addFieldType(bookIntroField)
                .build();
        System.out.println("Creating example collection: " + collectionName);
        System.out.println("Schema: " + createCollectionParam);
        milvusClient.createCollection(createCollectionParam);
        System.out.println("Success!");

        //insert data with customized ids
        Random ran = new Random();
        int singleNum = 1000;
        int insertRounds = 2;
        long insertTotalTime = 0L;
        System.out.println("Inserting " + singleNum * insertRounds + " entities... ");
        for (int r = 0; r < insertRounds; r++) {
            List<Long> book_id_array = new ArrayList<>();
            List<Long> word_count_array = new ArrayList<>();
            List<List<Float>> book_intro_array = new ArrayList<>();
            for (long i = r * singleNum; i < (r + 1) * singleNum; ++i) {
                book_id_array.add(i);
                word_count_array.add(i + 10000);
                List<Float> vector = new ArrayList<>();
                for (int k = 0; k < dim; ++k) {
                    vector.add(ran.nextFloat());
                }
                book_intro_array.add(vector);
            }
            List<InsertParam.Field> fields = new ArrayList<>();
            fields.add(new InsertParam.Field(bookIdField.getName(), book_id_array));
            fields.add(new InsertParam.Field(wordCountField.getName(), word_count_array));
            fields.add(new InsertParam.Field(bookIntroField.getName(), book_intro_array));
            InsertParam insertParam = InsertParam.newBuilder()
                    .withCollectionName(collectionName)
                    .withFields(fields)
                    .build();
            long startTime = System.currentTimeMillis();
            R<MutationResult> insertR = milvusClient.insert(insertParam);
            long endTime = System.currentTimeMillis();
            insertTotalTime += (endTime - startTime) / 1000.00;
        }
        System.out.println("Succeed in " + insertTotalTime + " seconds!");
        // flush data
        System.out.println("Flushing...");
        long startFlushTime = System.currentTimeMillis();
        milvusClient.flush(FlushParam.newBuilder()
                .withCollectionNames(Collections.singletonList(collectionName))
                .withSyncFlush(true)
                .withSyncFlushWaitingInterval(50L)
                .withSyncFlushWaitingTimeout(30L)
                .build());
        long endFlushTime = System.currentTimeMillis();
        System.out.println("Succeed in " + (endFlushTime - startFlushTime) / 1000.00 + " seconds!");

        // build index
        System.out.println("Building AutoIndex...");
        final IndexType INDEX_TYPE = IndexType.AUTOINDEX;   // IndexType
        long startIndexTime = System.currentTimeMillis();
        R<RpcStatus> indexR = milvusClient.createIndex(
                CreateIndexParam.newBuilder()
                        .withCollectionName(collectionName)
                        .withFieldName(bookIntroField.getName())
                        .withIndexType(INDEX_TYPE)
                        .withMetricType(MetricType.L2)
                        .withSyncMode(Boolean.TRUE)
                        .withSyncWaitingInterval(500L)
                        .withSyncWaitingTimeout(30L)
                        .build());
        long endIndexTime = System.currentTimeMillis();
        System.out.println("Succeed in " + (endIndexTime - startIndexTime) / 1000.00 + " seconds!");

        // load collection
        System.out.println("Loading collection...");
        long startLoadTime = System.currentTimeMillis();
        milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .withSyncLoad(true)
                .withSyncLoadWaitingInterval(500L)
                .withSyncLoadWaitingTimeout(100L)
                .build());
        long endLoadTime = System.currentTimeMillis();
        System.out.println("Succeed in " + (endLoadTime - startLoadTime) / 1000.00 + " seconds");

        // search
        final Integer SEARCH_K = 2;                       // TopK
        final String SEARCH_PARAM = "{\"nprobe\":10}";    // Params
        List<String> search_output_fields = Arrays.asList("book_id", "word_count");
        for (int i = 0; i < 10; i++) {
            List<Float> floatList = new ArrayList<>();
            for (int k = 0; k < dim; ++k) {
                floatList.add(ran.nextFloat());
            }
            List<List<Float>> search_vectors = Collections.singletonList(floatList);
            SearchParam searchParam = SearchParam.newBuilder()
                    .withCollectionName(collectionName)
                    .withMetricType(MetricType.L2)
                    .withOutFields(search_output_fields)
                    .withTopK(SEARCH_K)
                    .withVectors(search_vectors)
                    .withVectorFieldName(bookIntroField.getName())
                    .withParams(SEARCH_PARAM)
                    .build();
            long startSearchTime = System.currentTimeMillis();
            R<SearchResults> search = milvusClient.search(searchParam);
            long endSearchTime = System.currentTimeMillis();
            System.out.println("Searching vector: " + search_vectors);
            System.out.println("Result: " + search.getData().getResults().getFieldsDataList());
            System.out.println("search " + i + " latency: " + (endSearchTime - startSearchTime) / 1000.00 + " seconds");
        }

        milvusClient.close();
    }

}
