drop table if exists fund;

create table fund(
	id int not null auto_increment comment '主键',
	code varchar(255) not null comment '基金代码',
	name varchar(255) not null comment '基金名称',
	net_value decimal(10, 4) not null comment '单位净值',
	yield_of_one_year varchar(255) not null comment '近1年收益率',
	yield_of_two_year varchar(255) not null comment '近2年收益率',
	yield_of_three_year varchar(255) not null comment '近3年收益率',
	yield_of_five_year varchar(255) not null comment '近5年收益率',
	type int not null comment '基金类型（1：股票型，2：混合型，3：债券型，4：指数型，5：QDII）',
	established_time datetime comment '成立时间',
	asset varchar(255) comment '基金规模，资产',
	manager varchar(255) comment '基金经理',
	status int default 1 not null comment '基金状态（1：正常开放，2：暂停交易）',
	is_deleted int default 0 not null comment '是否删除（0：有效，1：无效删除）',
	created_time datetime default now() not null comment '创建时间',
	updated_time datetime default now() not null comment '更新时间',
	primary key(id)
) comment = '基金表';

create unique index unq_idx_type_code on fund(type, code);
create index idx_type_name_code on fund(type, name, code);