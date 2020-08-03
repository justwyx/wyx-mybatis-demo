package com.wyx.jdbc.framework.config;

import com.wyx.jdbc.sqlsource.SqlSource;

/**
 * @author : Just wyx
 * @Description : 封装了statement标签中的信息
 * @Date : 2020/8/1
 */
public class MappedStatement {
	private String statementId;

	private String statementType;

	private String resultType;

	private Class resultTypeClass;

	private SqlSource sqlSource;


	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public Class getResultTypeClass() {
		return resultTypeClass;
	}

	public void setResultTypeClass(Class resultTypeClass) {
		this.resultTypeClass = resultTypeClass;
	}

	public SqlSource getSqlSource() {
		return sqlSource;
	}

	public void setSqlSource(SqlSource sqlSource) {
		this.sqlSource = sqlSource;
	}
}
