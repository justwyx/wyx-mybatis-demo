package com.wyx.mybatis.v3.session;

import com.wyx.mybatis.v3.executor.*;
import com.wyx.mybatis.v3.executor.parameter.DefaultParameterHandler;
import com.wyx.mybatis.v3.executor.parameter.ParameterHandler;
import com.wyx.mybatis.v3.executor.resultset.DefaultResultSetHandler;
import com.wyx.mybatis.v3.executor.resultset.ResultSetHandler;
import com.wyx.mybatis.v3.executor.statement.CallableStatementHandler;
import com.wyx.mybatis.v3.executor.statement.PreparedStatementHandler;
import com.wyx.mybatis.v3.executor.statement.StatementHandler;
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

	private boolean useCache;


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

	public Executor newExecutor(String executorType) {
		executorType = executorType == null || executorType.equals("")? "simple" : executorType;
		Executor executor = null;
		if (executorType.equals("simple")) {
			executor = new SimpleExecutor();
		} else if(executorType.equals("batch")) {
			executor = new BatchExecutor();
		} else if(executorType.equals("reuse")) {
			executor = new ReuseExecutor();
		} else {
//			throw
		}

		// 如果二级缓存开启，对真正的执行器进行包装
		if (useCache) {
			executor = new CachingExecutor(executor);
		}

		return executor;
	}

	public StatementHandler newStatementHandler(String statementType) {
		statementType = statementType == null || statementType.equals("")? "prepared" : statementType;
		// routingStatementHandler

		if (statementType.equals("prepared")) {
			return new PreparedStatementHandler(this);
		}
//		else if (statementType.equals("prepared")) {
//			return new CallableStatementHandler();
//		}

		return null;
	}

	public ParameterHandler newParameterHandler() {
		return new DefaultParameterHandler();
	}

	public ResultSetHandler newResultSetHandler() {
		return new DefaultResultSetHandler();
	}
}
