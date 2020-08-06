package com.wyx.jdbc.framework.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : 封装sql语句和#{}解析出来的参数信息集合
 * @Date : 2020/8/4
 */
public class BoundSql {
	// 已经解析完的sql语句
	private String sql;

	private List<ParameterMapping> parameterMappings = new ArrayList<>();

	public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void setParameterMappings(List<ParameterMapping> parameterMappings) {
		this.parameterMappings = parameterMappings;
	}
}
