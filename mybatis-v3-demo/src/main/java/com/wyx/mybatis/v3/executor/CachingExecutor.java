package com.wyx.mybatis.v3.executor;

import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.session.Configuration;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : 用于处理二级缓存
 * @Date : 2020/8/9
 */
public class CachingExecutor implements Executor{
	// 委托给真正的执行器处理
	private Executor executor;

	public CachingExecutor(Executor executor) {
		this.executor = executor;
	}

	@Override
	public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
		// 先获取二级缓存 对象

		// 二级缓存中没有，则继续调用执行器逻辑

		return null;
	}
}
