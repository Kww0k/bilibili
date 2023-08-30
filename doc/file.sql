create table files
(
    id          int auto_increment
        primary key,
    name        varchar(255)  null comment '文件名',
    type        varchar(255)  null comment '类型',
    file_type   varchar(255)  null comment '文件类型',
    url         varchar(2048) null comment '文件地址',
    create_time varchar(255)  null comment '创建时间',
    create_by   int           null comment '创建人',
    update_time varchar(255)  null comment '更新时间',
    update_by   int           null comment '更新人',
    del_flag    int default 0 null,
    constraint files_pk
        unique (name)
);

