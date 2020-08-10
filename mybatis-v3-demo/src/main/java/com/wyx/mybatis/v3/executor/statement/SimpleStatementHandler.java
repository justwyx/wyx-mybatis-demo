package com.wyx.mybatis.v3.executor.statement;

import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.mapping.ParameterMapping;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : 用于处理PreparedStatement对象
 * @Date : 2020/8/9
 */
public class SimpleStatementHandler implements StatementHandler{
	@Override
	public Statement prepare( String sql, Connection connection) {
		return null;
	}

	@Override
	public void setParameters(Object param, Statement statement, BoundSql boundSql) throws SQLException {

	}


	@Override
	public List<Object> query(Statement statement, MappedStatement mappedStatement, List<Object> resultList) {
		return null;
	}
}
