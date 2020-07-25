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