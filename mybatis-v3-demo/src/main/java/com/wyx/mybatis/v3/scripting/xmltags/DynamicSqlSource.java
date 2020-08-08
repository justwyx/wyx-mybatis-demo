package com.wyx.mybatis.v3.scripting.xmltags;


import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.SqlSource;
import com.wyx.mybatis.v3.session.Configuration;
import com.wyx.mybatis.v3.utils.GenericTokenParser;
import com.wyx.mybatis.v3.utils.ParameterMappingTokenHandler;

/**
 * @author : Just wyx
 * @Description : 保存的是带有${}或者动态标签的SqlNode
 * @Date : 2020/8/8
 */
public class DynamicSqlSource implements SqlSource {
//	private final Configuration configuration;

	private final SqlNode rootSqlNode;

	public DynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
//		this.configuration = configuration;
		this.rootSqlNode = rootSqlNode;
	}


	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		// 获取DynamicContext对象,将入参封装至其中，因为解析${}和动态标签时会用到
		DynamicContext context = new DynamicContext(parameterObject);
		// 将sqlNode中的sql进行组装，同时解析掉${}和动态标签
		rootSqlNode.apply(context);
		// 获取sqlText，此时的sqlText可能还包含#{}
		String sqlText = context.getSql();
		// 解析#{}，替换成占位符
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
		// 获取解析完成的sql
		String sql = tokenParser.parse(sqlText);

		return new BoundSql(sql, tokenHandler.getParameterMappings());
	}
}
