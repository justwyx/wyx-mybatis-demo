package com.wyx.jdbc.sqlsource;

import com.wyx.jdbc.framework.config.BoundSql;
import com.wyx.jdbc.framework.config.DynamicContext;
import com.wyx.jdbc.sqlnode.SqlNode;
import com.wyx.jdbc.utils.GenericTokenParser;
import com.wyx.jdbc.utils.ParameterMappingTokenHandler;

/**
 * @Description :
 * 封装了${}和动态标签的sql信息，并提供对他们的处理接口
 * 注意
 * 每一次处理${}或者动态标签，都要根据入参对象，重新生成新的sql语句
 * @author : Just wyx
 * @Date : 2020/8/4
 */
public class DynamicSqlSource implements SqlSource{
	/**
	 * 封装了带有${}或者动态标签的sql脚本
	 */
	private SqlNode mixedSqlNode;

	public DynamicSqlSource(SqlNode mixedSqlNode) {
		this.mixedSqlNode = mixedSqlNode;
	}

	@Override
	public BoundSql getBoundSql(Object param) {
		// 1.处理所有的SQL节点，获取合并之后的完整的SQL语句（可能还带有#{}）
		DynamicContext context = new DynamicContext(param);
		mixedSqlNode.apply(context);
		String sqlText = context.getSql();
		// 2.调用sqlsource的处理逻辑，对于#{}进行处理
		// 处理#{}
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
		String sql = tokenParser.parse(sqlText);

		return new BoundSql(sql, tokenHandler.getParameterMappings());
	}
}
