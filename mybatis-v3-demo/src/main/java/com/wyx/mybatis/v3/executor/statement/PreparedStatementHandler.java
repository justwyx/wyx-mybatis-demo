package com.wyx.mybatis.v3.executor.statement;

import com.wyx.mybatis.v3.executor.parameter.ParameterHandler;
import com.wyx.mybatis.v3.executor.resultset.ResultSetHandler;
import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.mapping.ParameterMapping;
import com.wyx.mybatis.v3.session.Configuration;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : 用于处理PreparedStatement对象
 * @Date : 2020/8/9
 */
public class PreparedStatementHandler implements StatementHandler{

	// 四大组件中的另外两大组件
	private ParameterHandler parameterHandler;

	private ResultSetHandler resultSetHandler;

	public PreparedStatementHandler(Configuration configuration) {
		parameterHandler = configuration.newParameterHandler();
		resultSetHandler = configuration.newResultSetHandler();
	}

	@Override
	public Statement prepare(String sql, Connection connection) throws SQLException {
		return connection.prepareStatement(sql);
	}

	@Override
	public void setParameters(Object param, Statement statement, BoundSql boundSql) throws SQLException {
		PreparedStatement preparedStatement = (PreparedStatement) statement;
		parameterHandler.setParameters(param, preparedStatement, boundSql);


	}

	@Override
	public List<Object> query(Statement statement, MappedStatement mappedStatement, List<Object> resultList) throws Exception {
		PreparedStatement preparedStatement = (PreparedStatement) statement;
		ResultSet resultSet = preparedStatement.executeQuery();

		return resultSetHandler.handleResultSet(preparedStatement, resultSet, mappedStatement);
	}
}
