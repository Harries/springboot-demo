参照mysql模块的 docker-compose启动

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