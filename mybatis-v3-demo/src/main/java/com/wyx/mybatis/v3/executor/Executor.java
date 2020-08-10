package com.wyx.mybatis.v3.executor;

import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.session.Configuration;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : 用于执行JDBC逻辑
 * @Date : 2020/8/9
 */
public interface Executor {
	<T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param);
}
