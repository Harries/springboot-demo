package com.et.imagesearch;

public class ImageSearcher {
    private MilvusClient client;

    public ImageSearcher() {
        // 连接到Milvus
        client = new MilvusGrpcClient(MilvusServiceClientConfig.builder().build());
    }

    public void search(INDArray queryFeatures) {
        // 在Milvus中进行相似性搜索
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName("image_collection")
                .withMetricType(MetricType.L2)
                .withTopK(5)
                .addVectorField("embedding", queryFeatures.toFloatVector())
                .build();

        SearchResults searchResults = client.search(searchParam);

        // 输出结果
        for (SearchResultsWrapper.IDScore idScore : searchResults.getIDScore(0)) {
            System.out.println("ID: " + idScore.getID() + ", Distance: " + idScore.getScore());
        }
    }
}