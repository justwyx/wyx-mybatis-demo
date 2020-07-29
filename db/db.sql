use wyx_mybatis_demo;
create table country(
id int not null AUTO_INCREMENT,
countryname varchar(255) null,
countrycode varchar(255) null,
PRIMARY KEY (`id`)
);
insert country(countryname, countrycode)
values ('中国', 'CN'),
('美国', 'US'),
('俄罗斯', 'RU'),
('英国', 'GB'),
('法国', 'FR');



/*************************************  一对一关系映射表  *******************************************/
DROP TABLE IF EXISTS one_to_one_card;
CREATE TABLE one_to_one_card (
id INT ( 11 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增_id',
id_card VARCHAR ( 20 ) NOT NULL DEFAULT '' COMMENT '证件号',
name VARCHAR ( 20 ) NOT NULL DEFAULT '' COMMENT '姓名',
sex TINYINT ( 4 ) NOT NULL DEFAULT 0 COMMENT '性别:1-男,2-女,0-未知',
PRIMARY KEY ( `id` ) USING BTREE,
UNIQUE KEY `ux_id_card` ( `id_card` ) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8 COMMENT = '一对一关系-证件表';

INSERT INTO one_to_one_card ( id_card, name, sex )
VALUES
	( '444400111122220001', 'wyx', 1 );

DROP TABLE IF EXISTS one_to_one_person;
CREATE TABLE one_to_one_person (
id INT ( 11 ) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增_id',
id_card VARCHAR ( 20 ) NOT NULL DEFAULT '' COMMENT '证件号',
nick_name VARCHAR ( 20 ) NOT NULL DEFAULT '' COMMENT '昵称',
PRIMARY KEY ( `id` ) USING BTREE,
UNIQUE KEY `ux_id_card` ( `id_card` ) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8 COMMENT = '一对一关系-用户表';

INSERT INTO one_to_one_person ( id_card, nick_name )
VALUES
	( '444400111122220001', 'jack' );




/*************************************  一对多关系映射表  *******************************************/
drop table if exists one_to_many_clazz;
CREATE TABLE one_to_many_clazz(
id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增_id',
clazz_no VARCHAR(8) NOT NULL DEFAULT '' COMMENT '班级编号',
name VARCHAR(20) NOT NULL DEFAULT '' COMMENT '班级名',
PRIMARY KEY ( `id` ) USING BTREE,
UNIQUE KEY `ux_clazz_no` ( `clazz_no` ) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8 COMMENT = '一对多关系-班级表';

INSERT INTO one_to_many_clazz(clazz_no,NAME) VALUES('2020001','2020一班');
INSERT INTO one_to_many_clazz(clazz_no,NAME) VALUES('2020002','2020二班');

drop table if exists one_to_many_student;
CREATE TABLE one_to_many_student(
id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增_id',
clazz_no VARCHAR(8) NOT NULL DEFAULT '' COMMENT '班级编号',
name VARCHAR(20) NOT NULL DEFAULT '' COMMENT '姓名',
sex TINYINT ( 4 ) NOT NULL DEFAULT 0 COMMENT '性别:1-男,2-女,0-未知',
PRIMARY KEY ( `id` ) USING BTREE,
KEY `idx_clazz_no` ( `clazz_no` ) USING BTREE
)ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8 COMMENT = '一对多关系-学生表';

INSERT INTO one_to_many_student (clazz_no,NAME,sex) VALUES('2020001','张三','1') ;
INSERT INTO one_to_many_student (clazz_no,NAME,sex) VALUES('2020001','李四','1') ;
INSERT INTO one_to_many_student (clazz_no,NAME,sex) VALUES('2020001','王五','1') ;
INSERT INTO one_to_many_student (clazz_no,NAME,sex) VALUES('2020002','赵六','1') ;
