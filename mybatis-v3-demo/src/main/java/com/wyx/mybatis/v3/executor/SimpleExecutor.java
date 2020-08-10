package com.wyx.mybatis.v3.executor;

import com.wyx.mybatis.v3.executor.statement.StatementHandler;
import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.session.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : 处理普通的JDBC执行操作，默认也是这个Executor
 * @Date : 2020/8/9
 */
public class SimpleExecutor extends BaseExecutor{
	@Override
	protected List<Object> queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
		// 声明返回对象
		List<Object> resultList = new ArrayList<>();
		// 连接对象
		Connection connection;
		// 预处理对象
		Statement statement = null;
		// 结果集对象
		ResultSet resultSet = null;
		try {
			// 通过连接池获取数据库连接
			connection = getConnection(configuration);
			// 获取sql,此时的sql已经${}替换
			String sql = boundSql.getSql();
			// 创建 statement
			// StatementHandler用于处理statement操作（statement, prepared, callable）
			// 通过StatementHandler去屏蔽不同的statement处理逻辑
			StatementHandler statementHandler = configuration.newStatementHandler(mappedStatement.getStatementType());
			statement = statementHandler.prepare(sql, connection);
			// 设置参数
			statementHandler.setParameters(param, statement, boundSql);
			// 执行Statement
			resultList = statementHandler.query(statement, mappedStatement, resultList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/**
			 * 释放资源,由于connection现在连接池管理，不需要手动释放
			 * 其它资源继续逆向释放
			 */
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
		}
		return resultList;
	}

	private Connection getConnection(Configuration configuration) throws SQLException {
		DataSource dataSource = configuration.getDataSource();
		return dataSource.getConnection();
	}
}
