package com.wyx.mybatis.v3.session.defaults;

import com.wyx.mybatis.v3.executor.Executor;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.session.Configuration;
import com.wyx.mybatis.v3.session.SqlSession;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class DefaultSqlSession implements SqlSession {
	private Configuration configuration;

	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public <T> T selectOne(String statement, Object parameter) {

		// Popular vote was to return null on 0 results and throw exception on too many.
		List<T> list = this.selectList(statement, parameter);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
//			throw new Exception("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
			return null;
		} else {
			return null;
		}
	}

	@Override
	public <E> List<E> selectList(String statement) {
		return null;
	}

	@Override
	public <E> List<E> selectList(String statementId, Object parameter) {
		MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
		// Executor 有多种类型，如SimpleExecutor,ReuseExecutor,BatchExecutor
		// 可以通过全局配置文件的settings去指定(该信息存储在Configuration对象中)
		// 可以通过创建SqlSession时指定executorType为以上三种类型之一
		Executor executor = configuration.newExecutor(null);
		// 委托给executor去处理JDBC操作
		return executor.query(configuration, mappedStatement, parameter);
	}
}
