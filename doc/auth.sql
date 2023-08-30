create table account
(
    id          int auto_increment comment '用户id'
        primary key,
    username    varchar(18)                                              null comment '用户名',
    nickname    varchar(50)   default 'bi_新用户'                        null comment '昵称',
    password    varchar(255)                                             null comment '密码',
    email       varchar(100)                                             null comment '邮箱地址',
    url         varchar(2048) default 'src/assets/img/defaultAvatar.jpg' null comment '头像地址',
    birthday    varchar(255)                                             null comment '生日',
    sex         char(2)       default '保密'                             null comment '性别',
    school      varchar(255)                                             null comment '学校',
    signature   varchar(2048)                                            null comment '签名',
    create_time varchar(255)                                             null comment '创建时间',
    create_by   int                                                      null comment '创建人',
    update_time varchar(255)                                             null comment '更新时间',
    update_by   int                                                      null comment '更新人',
    del_flag    int           default 0                                  null comment '逻辑删除',
    constraint account_pk
        unique (username),
    constraint account_pk2
        unique (email)
);