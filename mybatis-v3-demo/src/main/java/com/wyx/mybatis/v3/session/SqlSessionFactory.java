package com.wyx.mybatis.v3.session;

/**
 * @Description : 用于创建SqlSession
 * @author : Just wyx
 * @Date : 2020/8/9
 */
public interface SqlSessionFactory {
	SqlSession openSession();
}
