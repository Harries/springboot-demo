## install kudu

详见代码仓库kudu模块里面的docker目录

## install impala
```shell
docker run -d --name kudu-impala --add-host  kudu-master-1:{ip} --add-host  kudu-master-2:{ip} --add-host  kudu-master-3:{ip} -p 21000:21000 -p 21050:21050 -p 25000:25000 -p 25010:25010 -p 25020:25020   --memory=4096m apache/kudu:impala-latest impala

```
需要注意增加主机映射关系，不然impala找不带kudu的机器。
访问http://172.24.4.35:25000/

## run impala-shell
```shell
docker exec -it kudu-impala impala-shell
```
### Create a Kudu Table
Now that you are in an impala-shell that is connected to Impala you can use an Impala DDL statement to create a Kudu table.
```
CREATE TABLE my_first_table
(
id BIGINT,
name STRING,
PRIMARY KEY(id)
)
PARTITION BY HASH PARTITIONS 4
STORED AS KUDU;

DESCRIBE my_first_table;
```
### Insert and Modify Data
With my_first_table created you can now use Impala DML statements to INSERT, UPDATE, UPSERT, and DELETE data.
```
-- Insert a row.
INSERT INTO my_first_table VALUES (99, "sarah");
SELECT * FROM my_first_table;

-- Insert multiple rows.
INSERT INTO my_first_table VALUES (1, "john"), (2, "jane"), (3, "jim");
SELECT * FROM my_first_table;

-- Update a row.
UPDATE my_first_table SET name="bob" where id = 3;
SELECT * FROM my_first_table;

-- Use upsert to insert a new row and update another.
UPSERT INTO my_first_table VALUES (3, "bobby"), (4, "grant");
SELECT * FROM my_first_table;

-- Delete a row.
DELETE FROM my_first_table WHERE id = 99;
SELECT * FROM my_first_table;

-- Delete multiple rows.
DELETE FROM my_first_table WHERE id < 3;
SELECT * FROM my_first_table;
```

## install hue
```shell
docker run -it -p 8888:8888 gethue/hue:latest
```
### 拷贝配置文件出来
```shell
docker cpb72dfc588c76:/usr/share/hue/desktop/conf/z-hue-overrides.ini ./z-hue-overrides.ini
```
编辑文件内容
```
[[database]]
engine=mysql
host=xxx.xxx.xxx.xxx
port=3306
user=xxx
password=xxx
name=database

[impala]
server_host=xxx.xxx.xxx.xxx
server_port=21050
```
### 将修改好的文件放回去
```shell
docker cp./z-hue-overrides.ini b72dfc588c76:/usr/share/hue/desktop/conf/z-hue-overrides.ini
```
重启
```shell
docker stop ${container_id}
docker start ${container_id}
```
访问http://172.24.4.35:8888/