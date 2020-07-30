package com.wyx.springbootmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages="com.wyx.springbootmybatis.mapper.read", sqlSessionTemplateRef="readSqlSessionTemplate")
@MapperScan(basePackages="com.wyx.springbootmybatis.mapper.write", sqlSessionTemplateRef="writeSqlSessionTemplate")
@SpringBootApplication
public class SpringbootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}

}
