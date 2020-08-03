package com.wyx.jdbc.framework.config;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : 封装了mybatis中xml文件的所有配置信息
 * @Date : 2020/8/1
 */
public class Configuratoin {
	private DataSource dataSource;

	private Map<String, MappedStatement> mappedStatementMap;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MappedStatement getMappedStatementById(String statementId) {
		return mappedStatementMap.get(statementId);
	}

	public void setMappedStatementMap(String statementId, MappedStatement mappedStatement) {
		this.mappedStatementMap.put(statementId, mappedStatement);
	}
}
