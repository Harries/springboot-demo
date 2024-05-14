### 第一个mysql数据库
```shell
docker run --name docker-mysql -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql
```

### 第二个mysql数据库
```shell
docker run --name docker-mysql-2 -e MYSQL_ROOT_PASSWORD=123456 -p 3307:3306 -d mysql
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
```
### 说明
```
msyql账号root
mysql密码123456
```

