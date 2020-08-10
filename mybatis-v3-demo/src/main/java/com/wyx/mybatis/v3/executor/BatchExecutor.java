package com.wyx.mybatis.v3.executor;

import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.session.Configuration;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class BatchExecutor extends BaseExecutor{

	@Override
	protected List<Object> queryFromDataBase(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object param) {
		return null;
	}
}
