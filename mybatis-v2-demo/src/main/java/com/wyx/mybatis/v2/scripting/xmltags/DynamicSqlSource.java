package com.wyx.mybatis.v2.scripting.xmltags;

import com.wyx.mybatis.v2.mapping.BoundSql;
import com.wyx.mybatis.v2.mapping.SqlSource;
import com.wyx.mybatis.v2.utils.GenericTokenParser;
import com.wyx.mybatis.v2.utils.ParameterMappingTokenHandler;

/**
 * @Description : 封装了${}或有动态标签的sql信息
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class DynamicSqlSource implements SqlSource {

	private final SqlNode mixedSqlNode;

	public DynamicSqlSource(SqlNode rootSqlNode) {
		this.mixedSqlNode = rootSqlNode;
	}


	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		// 需要将${}和动态sql语句进行替换,需要根据入参进行处理
		DynamicContext context = new DynamicContext(parameterObject);
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

		return new BoundSql(sql, tokenHandler.getParameterMappings());
	}
}
