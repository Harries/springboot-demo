### Redis

```shell
# 当前目录下所有文件赋予权限(读、写、执行)
chmod -R 777 ./redis
chmod -R 777 ./redis-master-slave
chmod -R 777 ./redis-master-slave-sentinel
chmod -R 777 ./redis-cluster
# 运行 -- 单机模式
docker-compose -f docker-compose-redis.yml -p redis up -d
# 运行 -- 主从复制模式（主写从读）
docker-compose -f docker-compose-redis-master-slave.yml -p redis up -d
# 运行 -- 哨兵模式（sentinel监视redis主从服务，当某个master服务下线时，自动将该master下的某个从服务升级为master服务替代已下线的master服务继续处理请求 -- 即主节点切换）
docker-compose -f docker-compose-redis-master-slave-sentinel.yml -p redis up -d
# 运行 -- Redis Cluster 集群
docker-compose -f docker-compose-redis-cluster.yml -p redis up -d
```

###### 连接redis

```shell
docker exec -it redis redis-cli -a 123456  # 密码为123456
```

###### 哨兵模式查看

```shell
# 连接
docker exec -it redis-sentinel-1 redis-cli -p 26379 -a 123456
# 查看redis主信息
sentinel master mymaster
# 查看redis从信息
sentinel slaves mymaster
```

###### Redis Cluster 集群

redis.conf中主要新增了如下配置

```
cluster-enabled yes
cluster-config-file nodes-6379.conf
cluster-node-timeout 15000
```

创建集群

```shell
docker exec -it redis-6381 redis-cli -h 172.22.0.11 -p 6381 -a 123456 --cluster create 172.22.0.11:6381 redis-6382:6382 redis-6383:6383 redis-6384:6384 redis-6385:6385 redis-6386:6386 --cluster-replicas 1
```

查看集群

```shell
# 连接集群某个节点
docker exec -it redis-6381 redis-cli -c -h redis-6381 -p 6381 -a 123456
# 查看集群信息
cluster info
# 查看集群节点信息
cluster nodes
# 查看slots分片
cluster slots
```

### Redis Manager

> Redis 一站式管理平台，支持集群（cluster、master-replica、sentinel）的监控、安装（除sentinel）、管理、告警以及基本的数据操作功能

```shell
docker-compose -f docker-compose-redis-manager.yml -p redis-manager up -d
```

web管理端：[`ip地址:8182`](http://www.zhengqingya.com:8182)
登录账号密码：`admin/admin`
