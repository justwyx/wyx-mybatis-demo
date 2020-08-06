package com.wyx.mybatis.v2.scripting.defaults;

import com.wyx.mybatis.v2.builder.StaticSqlSource;
import com.wyx.mybatis.v2.mapping.BoundSql;
import com.wyx.mybatis.v2.mapping.SqlSource;
import com.wyx.mybatis.v2.scripting.xmltags.DynamicContext;
import com.wyx.mybatis.v2.scripting.xmltags.SqlNode;
import com.wyx.mybatis.v2.utils.GenericTokenParser;
import com.wyx.mybatis.v2.utils.ParameterMappingTokenHandler;

/**
 * @Description : 封装的是不带${}与动态标签的Sql对象
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class RawSqlSource implements SqlSource {
	// 一个静态的sqlSource
	private SqlSource sqlSource;

	public RawSqlSource(SqlNode mixedSqlNode) {
		/**
		 * 处理所有sql节点,封装成一个静态的sqlSource
		 */
		// 由于没有动态sql语句，只需将有#{}的地文替换成?，所以无需入参
		DynamicContext context = new DynamicContext(null);
		mixedSqlNode.apply(context);
		// 这时获取到的sql可能还存在#{}没有被替换
		String sqlText = context.getSql();
		/**
		 * 调用sqlsource的处理逻辑，对#{}处理
		 */
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
		// 获取解析完成的sql
		String sql = tokenParser.parse(sqlText);
		// 封装成静态sql对象
		sqlSource = new StaticSqlSource(sql,tokenHandler.getParameterMappings());
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		// 去调用静态sqlSource的getBoundSql
		return sqlSource.getBoundSql(parameterObject);
	}
}
