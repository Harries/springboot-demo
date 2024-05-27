### 下载docker-compose项目
```shell
git clone https://github.com/pingcap/tidb-docker-compose.git
```

### 启动集群
```shell
cd tidb-docker-compose 
docker-compose up -d
```
### docker ps查看集群

可以看到，已经启动了三个tikv实例，一个tidb实例，三个pd实例，还有监控和tidb-vision。
- 监控的访问地址是 http://localhost:3000，用户名/密码：admin/admin。
- tidb-vision 的访问地址是 http://localhost:8010

### 使用MySQL客户端访问TiDB
```shell
mysql -h 127.0.0.1 -P 4000 -u root
```
### 集群停止
```shell
docker-compose down
```
