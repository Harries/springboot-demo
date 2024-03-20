参照mysql模块的 docker-compose启动

```
create table user_info
(
    user_id     varchar(64)          not null
        primary key,
    username    varchar(100)         null comment '用户名',
    age         int(3)               null comment '年龄',
    gender      tinyint(1)           null comment '字典类型',
    remark      varchar(255)         null comment '描述',
    create_time datetime             null comment '创建时间',
    create_id   varchar(64)          null comment '创建人ID',
    update_time datetime             null comment '修改时间',
    update_id   varchar(64)          null comment '修改人ID',
    enabled     tinyint(1) default 1 null comment '删除状态（1-正常，0-删除）'
)
    comment '字典表';
```