package com.wyx.mybatis.v2.mapping;

/**
 * @Description :
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class MappedStatement {
	/**
	 * namespace + id
	 */
	private String statementId;

	private String statementType;

	private String resultType;

	/**
	 * 返回结果类型
	 */
	private Class resultTypeClass;

	private SqlSource sqlSource;

	public MappedStatement(Builder builder) {
		this.statementId = builder.statementId;
		this.resultTypeClass = builder.resultTypeClass;
		this.statementType = builder.statementType;
		this.sqlSource = builder.sqlSource;
	}


	public static class Builder {

		private String statementId;

		private String statementType;

		private String resultType;

		private Class resultTypeClass;

		private SqlSource sqlSource;


		public MappedStatement build() {
			// 参数校验
			return new MappedStatement(this);
		}

		public Builder setStatementId(String statementId) {
			this.statementId = statementId;
			return this;
		}

		public Builder setStatementType(String statementType) {
			this.statementType = statementType;
			return this;
		}

		public Builder setResultType(String resultType) {
			this.resultType = resultType;
			return this;
		}

		public Builder setResultTypeClass(Class resultTypeClass) {
			this.resultTypeClass = resultTypeClass;
			return this;
		}

		public Builder setSqlSource(SqlSource sqlSource) {
			this.sqlSource = sqlSource;
			return this;
		}
	}


	public String getStatementId() {
		return statementId;
	}

	public String getStatementType() {
		return statementType;
	}

	public String getResultType() {
		return resultType;
	}

	public Class getResultTypeClass() {
		return resultTypeClass;
	}

	public SqlSource getSqlSource() {
		return sqlSource;
	}

}
