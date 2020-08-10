package com.wyx.mybatis.v3.session.defaults;

import com.wyx.mybatis.v3.session.Configuration;
import com.wyx.mybatis.v3.session.SqlSession;
import com.wyx.mybatis.v3.session.SqlSessionFactory;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
	private Configuration configuration;

	public DefaultSqlSessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public SqlSession openSession() {
		return new DefaultSqlSession(configuration);
	}
}
