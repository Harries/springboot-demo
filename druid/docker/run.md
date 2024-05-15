### mysql数据库
```shell
docker run --name docker-mysql-5.7 -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:5.7
```



### 初始化数据

```
create database demo;
create table user_info
(
user_id     varchar(64)          not null primary key,
username    varchar(100)         null ,
age         int(3)               null ,
gender      tinyint(1)           null ,
remark      varchar(255)         null ,
create_time datetime             null ,
create_id   varchar(64)          null ,
update_time datetime             null ,
update_id   varchar(64)          null ,
enabled     tinyint(1) default 1 null
);
INSERT INTO demo.user_info
(user_id, username, age, gender, remark, create_time, create_id, update_time, update_id, enabled)
VALUES('1', '1', 1, 1, '1', NULL, '1', NULL, NULL, 1);

```
### 说明
```
msyql account：root
mysql password：123456
```

