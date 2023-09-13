create table message
(
    id          bigint auto_increment
        primary key,
    content     varchar(500)  null comment '内容',
    from_id     int           null comment '发起人',
    to_id       int           null comment '接受人',
    create_time varchar(255)  null comment '创建时间',
    update_time varchar(255)  null comment '更新时间',
    del_flag    int default 0 null invisible
);
