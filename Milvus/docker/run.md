## CLoud
- First, we’ll need an instance of Milvus DB. The easiest and quickest way is to get a fully managed free Milvus DB instance provided by Zilliz Cloud:  
https://zilliz.com/

- For this, we’ll need to register for a Zilliz cloud account and follow the documentation for creating a free DB cluster.

## local 
```shell
curl -sfL https://raw.githubusercontent.com/milvus-io/milvus/master/scripts/standalone_embed.sh -o standalone_embed.sh

bash standalone_embed.sh start

```
ou can stop and delete this container as follows

### Stop Milvus
```shell
$ bash standalone_embed.sh stop
```
### Delete Milvus data
```shell
$ bash standalone_embed.sh delete
```
You can upgrade the latest version Milvus as follows

### upgrade Milvus
```shell
$ bash standalone_embed.sh upgrade
```