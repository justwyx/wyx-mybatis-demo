package com.wyx.mybatis.v3.session;

import com.wyx.mybatis.v3.mapping.MappedStatement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : 全局数据源对象与MappedStatement对象信息
 * @Date : 2020/8/8
 */
public class Configuration {
	private DataSource dataSource;

	private final Map<String, MappedStatement> mappedStatements = new HashMap<>();


	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MappedStatement getMappedStatement(String statementId) {
		return mappedStatements.get(statementId);
	}

	public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
		this.mappedStatements.put(statementId, mappedStatement);
	}

}
