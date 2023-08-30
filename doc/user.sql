create table todo
(
    id                int auto_increment comment 'id'
        primary key,
    todo_affair       varchar(1024)                null comment '代办事项',
    completion_status varchar(10) default '未完成' null comment '完成情况',
    create_time       varchar(255)                 null comment '创建时间',
    create_by         int                          null comment '创建人',
    update_time       varchar(255)                 null comment '更新时间',
    update_by         int                          null comment '更新人',
    del_flag          int         default 0        null,
    constraint todo_pk
        unique (id, create_by)
)
    comment '代办事项';

