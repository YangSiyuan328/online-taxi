-- auto-generated definition
create table passenger_user
(
    id               bigint(32) auto_increment comment '序号 主键 自动增长'
        primary key,
    gmt_create       datetime     null comment '创建时间',
    gmt_modified     datetime     null comment '更新时间',
    passenger_phone  varchar(16)  null comment '乘客电话',
    passenger_name   varchar(16)  null comment '乘客姓名',
    passenger_gender tinyint(1)   null comment '乘客性别 0:女 1:男',
    state            tinyint(1)   null comment '乘客状态 0 有效 1失效',
    profile_photo    varchar(128) null comment '头像图片地址的url'
);

