package com.wyx.mybatis.v3.session;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public interface SqlSession {

	<T> T selectOne(String statement, Object parameter);

	<E> List<E> selectList(String statement);

	/**
	 * 根据参数查询数据列表
	 */
	<E> List<E> selectList(String statement, Object parameter);
}
