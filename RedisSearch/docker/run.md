
start
```shell
docker-compose up -d
```
如果docker-compose 拉不起来，则手动执行
```shell
docker run -d --name redisinsight -p 5540:5540 redis/redisinsight:latest

```