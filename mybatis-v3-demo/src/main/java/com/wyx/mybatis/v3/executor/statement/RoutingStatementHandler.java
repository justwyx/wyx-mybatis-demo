package com.wyx.mybatis.v3.executor.statement;

import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.session.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : 路由StatementHandler
 * @Date : 2020/8/9
 */
public class RoutingStatementHandler implements StatementHandler {

//	private final StatementHandler delegate;

	public RoutingStatementHandler(String statementType, Configuration configuration) {
//		switch (ms.getStatementType()) {
//			case STATEMENT:
//				delegate = new SimpleStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
//				break;
//			case PREPARED:
//				delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
//				break;
//			case CALLABLE:
//				delegate = new CallableStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
//				break;
//			default:
//				throw new ExecutorException("Unknown statement type: " + ms.getStatementType());
//		}
	}

	@Override
	public Statement prepare(String sql, Connection connection) throws SQLException {
		return null;
	}

	@Override
	public void setParameters(Object param, Statement statement, BoundSql boundSql) throws SQLException {

	}

	@Override
	public List<Object> query(Statement statement, MappedStatement mappedStatement, List<Object> resultList) throws Exception {
		return null;
	}
}
