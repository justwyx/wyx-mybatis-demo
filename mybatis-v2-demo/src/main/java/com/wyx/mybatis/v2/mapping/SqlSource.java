package com.wyx.mybatis.v2.mapping;

/**
 * @Description :
 * 个人理解:
 * 		SqlSource用于保存通过拼接sqlNode获取的完整sql信息，保存至BoundSql对象中
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public interface SqlSource {
	BoundSql getBoundSql(Object parameterObject);
}
