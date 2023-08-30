create table tag
(
    id          int           not null
        primary key,
    name        varchar(255)  null,
    create_time varchar(255)  null comment '创建时间',
    create_by   int           null comment '创建人',
    update_time varchar(255)  null comment '更新时间',
    update_by   int           null comment '更新人',
    del_flag    int default 0 null,
    constraint tag_pk2
        unique (name)
);

create table video
(
    id          int auto_increment comment 'id'
        primary key,
    title       varchar(255)                  null comment '标题',
    description varchar(4096)                 null comment '描述',
    preview_url varchar(4096)                 null comment '封面图片地址',
    video_url   varchar(4096)                 null comment '视频地址',
    type_id     int                           null comment '对应标签id',
    status      varchar(10)  default '未审核' null comment '审核状态',
    visibility  varchar(255) default '所有人' null comment '谁可见',
    create_time varchar(255)                  null comment '创建时间',
    create_by   int                           null comment '创建人',
    update_time varchar(255)                  null comment '更新时间',
    update_by   int                           null comment '更新人',
    del_flag    int          default 0        null
);

create table danmaku
(
    id          int auto_increment
        primary key,
    content     varchar(50)                  null comment '内容',
    color       varchar(50)                  null comment '颜色',
    time        double                       null comment '弹幕出现时间',
    type        varchar(50) default 'scroll' null comment '弹幕类型',
    video_id    int                          null,
    create_time varchar(255)                 null comment '创建时间',
    create_by   int                          null comment '创建人',
    update_time varchar(255)                 null comment '更新时间',
    update_by   int                          null comment '更新人',
    del_flag    int         default 0        null comment '逻辑删除'
)
    comment '弹幕';

