CREATE DATABASE mall_order DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

create table `t_order` (
  `id` bigint unsigned auto_increment comment '主键',
  `order_no` bigint unsigned not null comment '订单号',
  `amount` decimal(10, 2) not null comment '总金额',
  `status` tinyint unsigned default 0 comment '状态 0-初始化 1-提交 2-完成 3-撤销',
  `user_id` bigint unsigned not null comment '用户ID',
  `coupon_record_id` bigint unsigned default null comment '优惠券ID',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  primary key pk_id (`id`),
  unique key uk_order_no(`order_no`),
  index idx_user_id(`user_id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='订单表';

create table `t_order_item` (
  `id` bigint unsigned auto_increment comment '主键',
  `order_no` bigint unsigned not null comment '订单号',
  `goods_id` bigint unsigned not null comment '商品ID',
  `number` int unsigned default 1 comment '数量',
  `amount` decimal(10, 2) default null comment '总金额',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  primary key pk_id (`id`),
  index idx_order_no(`order_no`),
  index idx_goods_id(`goods_id`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='订单条目表';

## 样本数据

```sql
USE `mall_order`;
INSERT INTO `t_order` (`order_no`, `amount`, `status`, `user_id`, `coupon_record_id`) VALUES (1, 848.00, 2, 1, 1);
INSERT INTO `t_order` (`order_no`, `amount`, `status`, `user_id`, `coupon_record_id`) VALUES (2, 848.00, 2, 2, 2);
INSERT INTO `t_order` (`order_no`, `amount`, `status`, `user_id`, `coupon_record_id`) VALUES (3, 848.00, 2, 3, 3);
INSERT INTO `t_order` (`order_no`, `amount`, `status`, `user_id`, `coupon_record_id`) VALUES (4, 848.00, 2, 4, 4);
INSERT INTO `t_order` (`order_no`, `amount`, `status`, `user_id`, `coupon_record_id`) VALUES (5, 848.00, 2, 5, 5);
INSERT INTO `t_order` (`order_no`, `amount`, `status`, `user_id`, `coupon_record_id`) VALUES (6, 848.00, 2, 6, 6);
INSERT INTO `t_order_item` (`order_no`, `goods_id`, `number`, `amount`) VALUES (1, 1, 1, 848.00);
INSERT INTO `t_order_item` (`order_no`, `goods_id`, `number`, `amount`) VALUES (2, 1, 1, 848.00);
INSERT INTO `t_order_item` (`order_no`, `goods_id`, `number`, `amount`) VALUES (3, 1, 1, 848.00);
INSERT INTO `t_order_item` (`order_no`, `goods_id`, `number`, `amount`) VALUES (4, 1, 1, 848.00);
INSERT INTO `t_order_item` (`order_no`, `goods_id`, `number`, `amount`) VALUES (5, 1, 1, 848.00);
INSERT INTO `t_order_item` (`order_no`, `goods_id`, `number`, `amount`) VALUES (6, 1, 1, 848.00);
```