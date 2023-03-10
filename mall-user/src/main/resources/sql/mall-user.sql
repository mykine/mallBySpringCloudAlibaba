CREATE DATABASE mall_user DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

create table `t_user` (
  `id` bigint unsigned auto_increment comment '主键',
  `account` varchar(32) not null comment '账号',
  `name` varchar(126) default '' comment '姓名',
  `password` varchar(128) default '' comment '密码',
  `role` tinyint unsigned default 1 comment '角色 1-普通用户 2-讲师',
  `status` tinyint unsigned default 0 comment '状态 0-锁定 1-正常',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  primary key pk_id (`id`),
  unique key uk_account(`account`)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='用户表';


## 样本数据

```sql
USE `mall_user`;
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhangsan', '张三', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('lisi', '李四', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('wangwu', '王五', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhaoliu', '赵六', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('sunqi', '孙七', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhouba', '周八', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('wujiu', '吴九', '123456', 1, 1);
INSERT INTO `t_user` (`account`, `name`, `password`, `role`, `status`) VALUES ('zhengshi', '郑十', '123456', 2, 1);
```
