package com.wyx.mybatis.v3.mapping;

import java.util.List;

/**
 * @Description : 保存的是sql与#{}替换的数据，源码中还有入参数据
 * @author : Just wyx
 * @Date : 2020/8/8
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
