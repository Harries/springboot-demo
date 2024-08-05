create table type_dict
(
    id          int auto_increment primary key,
    category    varchar(255) not null ,
    type_key    int          not null  ,
    type_value  varchar(255) null  ,
    is_enable   bit null  ,
    create_time datetime null
) collate = utf8mb4_general_ci;