package com.wyx.mybatis.v2.builder;

import com.wyx.mybatis.v2.mapping.BoundSql;
import com.wyx.mybatis.v2.mapping.ParameterMapping;
import com.wyx.mybatis.v2.mapping.SqlSource;

import java.util.List;

/**
 * @Description : 静态的sqlSource, 不需要要进行处理
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class StaticSqlSource implements SqlSource {

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
