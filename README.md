# mallBySpringCloudAlibaba

## 样本数据

```sql

USE `mall_goods`;
INSERT INTO `t_goods` (`name`, `description`, `type`, `price`, `stock`) VALUES ('分布式专题实战利器', '分布式架构是大型项目必用的架构方式，也是云原生、Serverless等新兴技术的底层基石。后端进阶，必然绕不开分布式。但市面的分布式课程鱼龙混杂，造成了不少学习精力的浪费。本课程由慕课网特邀大厂讲师制作，将目前的分布式技术知识进行整合，分为6大知识模块讲解学习，并深度结合案例，将每个细节讲深、讲透，帮助你打通进阶路上的关键脉络，后续学习也更快速。', 0, 399.00, 10000);

USE `mall_user`;
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhangsan', '张三', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('lisi', '李四', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('wangwu', '王五', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhaoliu', '赵六', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('sunqi', '孙七', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhouba', '周八', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('wujiu', '吴九', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhengshi', '郑十', '123456', 2, 1);

USE `mall_coupon`;
INSERT INTO `t_coupon` (`title`, `with_amount`, `used_amount`, `quota`, `take_count`, `used_count`, `status`) VALUES ('全新上线活动', 887.00, 39.00, 994, 6, 1, 1);
INSERT INTO `t_coupon_record` (`user_id`, `coupon_id`, `status`) VALUES (1, 1, 0);
INSERT INTO `t_coupon_record` (`user_id`, `coupon_id`, `status`) VALUES (2, 1, 0);
INSERT INTO `t_coupon_record` (`user_id`, `coupon_id`, `status`) VALUES (3, 1, 0);
INSERT INTO `t_coupon_record` (`user_id`, `coupon_id`, `status`) VALUES (4, 1, 0);
INSERT INTO `t_coupon_record` (`user_id`, `coupon_id`, `status`) VALUES (5, 1, 0);
INSERT INTO `t_coupon_record` (`user_id`, `coupon_id`, `status`) VALUES (6, 1, 1);

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