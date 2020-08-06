package com.wyx.mybatis.v2.session;

import com.wyx.mybatis.v2.mapping.MappedStatement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 用于保存数据源对象与MappedStatement对象信息
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class Configuration {
	private DataSource dataSource;

	private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MappedStatement getMappedStatement(String statementId) {
		return mappedStatementMap.get(statementId);
	}

	public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
		this.mappedStatementMap.put(statementId, mappedStatement);
	}
}
