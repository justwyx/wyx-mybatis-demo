package com.wyx.mybatis.v3.builder;

import com.wyx.mybatis.v3.session.Configuration;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public abstract class BaseBuilder {
	protected final Configuration configuration;

	public BaseBuilder(Configuration configuration) {
		this.configuration = configuration;
	}
}
