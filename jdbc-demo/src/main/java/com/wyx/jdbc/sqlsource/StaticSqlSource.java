package com.wyx.jdbc.sqlsource;

import com.wyx.jdbc.framework.config.BoundSql;
import com.wyx.jdbc.framework.config.ParameterMapping;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : 静态的sqlSource
 * @Date : 2020/8/4
 */
public class StaticSqlSource implements SqlSource{

	private String sql;

	private List<ParameterMapping> parameterMappingList;


	public StaticSqlSource(String sql, List<ParameterMapping> parameterMappingList) {
		this.sql = sql;
		this.parameterMappingList = parameterMappingList;
	}

	@Override
	public BoundSql getBoundSql(Object param) {
		return new BoundSql(sql, parameterMappingList);
	}
}
