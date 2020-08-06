package com.wyx.mybatis.v2.mapping;

import java.util.List;

/**
 * @Description : 封装sql语句和#{}解析出来的参数信息集合
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class BoundSql {
	/**
	 * 完整的sql语句,${}与动态sql已处理完
	 * #{}已替换成?
	 */
	private final String sql;

	private final List<ParameterMapping> parameterMappings;

	public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	public String getSql() {
		return sql;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}
}
