DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `username`, `birthday`, `sex`, `address`)
VALUES
	(1,'赵一','2013-07-10','2','天津市'),
	(2,'钱二','2014-07-10','1','北京市'),
	(3,'孙三','2015-07-10','1','杭州市'),
	(4,'李四','2016-07-10','1','上海市'),
	(5,'周五','2017-07-10','1','湖南省'),
	(6,'吴六','2018-07-10','1','河南省'),
	(7,'郑七','2019-07-10','1','北京市'),
	(8,'李四','2020-07-10','2','深圳市');