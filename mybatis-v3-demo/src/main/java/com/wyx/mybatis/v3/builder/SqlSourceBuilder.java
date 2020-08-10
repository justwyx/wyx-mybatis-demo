package com.wyx.mybatis.v3.builder;

import com.wyx.mybatis.v3.mapping.SqlSource;
import com.wyx.mybatis.v3.utils.GenericTokenParser;
import com.wyx.mybatis.v3.utils.ParameterMappingTokenHandler;

/**
 * @author : Just wyx
 * @Description : 用于处理#{},获取staticSqlSource
 * @Date : 2020/8/9
 */
public class SqlSourceBuilder {

	public SqlSource parse(String originalSql) {
		// 解析#{}，替换成占位符
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
		// 获取解析完成的sql
		String sql = tokenParser.parse(originalSql);
		// 封装成静态sql对象
		return new StaticSqlSource(sql,tokenHandler.getParameterMappings());
	}
}
