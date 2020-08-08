package com.wyx.mybatis.v3.builder;

import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.ParameterMapping;
import com.wyx.mybatis.v3.mapping.SqlSource;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : 静态SqlSource,封装的是解析完成的sql对象，只需要解析一次
 * @Date : 2020/8/8
 */
public class StaticSqlSource implements SqlSource {
	private final String sql;

	private final List<ParameterMapping> parameterMappings;

	public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		return new BoundSql(sql, parameterMappings);
	}
}
