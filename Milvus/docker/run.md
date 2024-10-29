## CLoud
- First, we’ll need an instance of Milvus DB. The easiest and quickest way is to get a fully managed free Milvus DB instance provided by Zilliz Cloud:  
https://zilliz.com/

- For this, we’ll need to register for a Zilliz cloud account and follow the documentation for creating a free DB cluster.

## local 
```shell


curl -sfL https://raw.githubusercontent.com/milvus-io/milvus/master/scripts/standalone_embed.sh -o standalone_embed.sh

bash standalone_embed.sh start


```

open url: http://127.0.0.1:19530