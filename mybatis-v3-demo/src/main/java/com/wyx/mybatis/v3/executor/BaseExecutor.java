package com.wyx.mybatis.v3.executor;

import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.mapping.SqlSource;
import com.wyx.mybatis.v3.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : SimpleExecutor,ReuseExecutor,BatchExecutor这三个类都会去走该类的一级缓存处理流程
 * @Date : 2020/8/9
 */
public abstract class BaseExecutor implements Executor{
	private Map<String, List<Object>> oneLevelCache = new HashMap<>();

	@Override
	public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
		// 获取缓存的key
		SqlSource sqlSource = mappedStatement.getSqlSource();
		BoundSql boundSql = sqlSource.getBoundSql(param);
		String cacheKey = getCacheKey(boundSql);
		List<Object> list = oneLevelCache.get(cacheKey);
		if (list != null) {
			return (List<T>) list;
		}

		// 查询数据库
		list = queryFromDataBase(configuration, mappedStatement, boundSql, param);

		// 存入一级缓存
		oneLevelCache.put(cacheKey, list);
		return (List<T>) list;
	}

	protected abstract List<Object> queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param);

	protected String getCacheKey(BoundSql boundSql) {
		// TODO cacheKey要做特殊处理
		//  boundSql.getSql() --> select *  from user where username = ?
		return boundSql.getSql();
	}
}
