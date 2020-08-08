package com.wyx.mybatis.v3.mapping;

/**
 * @Description : 封装的是完整的sql，封装在BoundSql对象中
 * @author : Just wyx
 * @Date : 2020/8/8
 */
public interface SqlSource {
	BoundSql getBoundSql(Object parameterObject);
}
