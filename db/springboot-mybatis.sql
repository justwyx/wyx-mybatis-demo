DROP TABLE IF EXISTS user;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(32) NOT NULL DEFAULT '' COMMENT '昵称',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机',
  `card_id` int(11) NOT NULL DEFAULT 0 COMMENT '证件id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dx_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';
INSERT into user(id,username,password,nickname,phone,card_id)
VALUES
(1, "admin","admin","管理员","13122223333",1),
(2, "user1","user1","用户1","13122220002",2);



DROP TABLE IF EXISTS card;
CREATE TABLE `card` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '证件id',
	`card_no` varchar(20) NOT NULL DEFAULT '' COMMENT '证件号',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名',
	`sex` tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='证件表';

DROP TABLE IF EXISTS role;
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名',
	`remarks` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

DROP TABLE IF EXISTS user_role_relation;
CREATE TABLE `user_role_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
	`role_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关系表';