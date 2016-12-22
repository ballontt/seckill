--数据库初始化脚本

--创建数据库
create database seckill;
--使用数据库
use seckill;
--创建秒杀库存表
create table seckill(
seckill_id bigint not null auto_increment comment '商品库存id',
name varchar(120) not null comment '商品名称',
number int not null comment '库存数量',
start_time TIMESTAMP NOT Null comment '秒杀开始时间',
end_time TIMESTAMP  NOT  NULL comment '秒杀结束时间',
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
PRIMARY KEY (seckill_id),
index idx_start_time(start_time),
index idx_end_time(end_time),
index idx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='秒杀库存';

--初始化数据
insert into
  seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒杀iphone7',100,'2016-11-11 00:00:00','2016-11-02 00：00：00'),
  ('500元秒杀ipad2',200,'2016-11-11 00:00:00','2016-11-02 00：00：00'),
  ('300元秒杀小米5',300,'2016-11-11 00:00:00','2016-11-02 00：00：00'),
  ('200元秒杀红米note',400,'2016-11-11 00:00:00','2016-11-02 00：00：00');

--秒杀成功明细表
--用户登录认证相关信息
CREATE TABLE success_killed(
seckill_id bigint not null comment '秒杀商品ID',
user_phone bigint not null comment '用户手机号',
state tinyint not null default -1 comment '状态标识：-1无效 0：成功 1：已付款',
create_time TIMESTAMP not null comment '创建时间',
primary key(seckill_id,user_phone),
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细';

--连接数据库
mysql -u ballontt -p
