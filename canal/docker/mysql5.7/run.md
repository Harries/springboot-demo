### MySQL

**注意my.cnf在windows系统必须为只读，否则忽略**

```shell
docker-compose -f docker-compose.yml -p mysql57 up -d
```
## 查看日志是否开启
```
show variables like 'log_%';
```
如果启用了，这个查询将返回log_bin的值为ON。

