CREATE DATABASE mall_goods DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

create table `t_goods` (
  `id` bigint unsigned auto_increment comment '主键',
  `name` varchar(100) not null comment '商品名称',
  `description` varchar(1000) default '' comment '商品描述',
  `type` tinyint unsigned default 0 comment '商品类型 1-免费课 2-实战课 3-体系课',
  `price` decimal(10, 2) not null comment '商品价格',
  `stock` bigint default -1 comment '商品库存，-1代表没有库存限制',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update
                                 current_timestamp comment '更新时间',
  primary key pk_id (`id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='商品表';


## 样本数据

```sql
USE `mall_goods`;
INSERT INTO `t_goods` (`name`, `description`, `type`, `price`, `stock`) VALUES ('分布式专题实战利器', '分布式架构是大型项目必用的架构方式，也是云原生、Serverless等新兴技术的底层基石。后端进阶，必然绕不开分布式。但市面的分布式课程鱼龙混杂，造成了不少学习精力的浪费。本课程由慕课网特邀大厂讲师制作，将目前的分布式技术知识进行整合，分为6大知识模块讲解学习，并深度结合案例，将每个细节讲深、讲透，帮助你打通进阶路上的关键脉络，后续学习也更快速。', 0, 399.00, 10000);
```