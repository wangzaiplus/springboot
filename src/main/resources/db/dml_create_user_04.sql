drop table if exists t_user;

create table t_user(
	id int not null auto_increment comment '主键',
	username varchar(255) not null comment '用户名',
	password varchar(255) not null comment '密码',
	password_salt varchar(255) not null comment '密码随机盐值',
	ip varchar(255) comment 'IP地址',
	mobile varchar(11) comment '手机号',
	mail varchar(255) comment '邮箱',
	type int default 0 not null comment '类型（0：普通用户，1：超级管理员）',
	status int default 0 not null comment '状态（0：正常，1：黑名单，2：已注销）',
	is_deleted int default 0 not null comment '是否删除（0：有效，1：无效删除）',
	created_time datetime default now() not null comment '创建时间',
	updated_time datetime default now() not null comment '更新时间',
	primary key(id)
) comment = '用户表';

create unique index unq_idx_username on t_user(username);