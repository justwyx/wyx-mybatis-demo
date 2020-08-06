package com.wyx.jdbc.sqlsource;

import com.wyx.jdbc.framework.config.BoundSql;
import com.wyx.jdbc.framework.config.DynamicContext;
import com.wyx.jdbc.sqlnode.SqlNode;
import com.wyx.jdbc.utils.GenericTokenParser;
import com.wyx.jdbc.utils.ParameterMappingTokenHandler;

/**
 * @Description :
 * 封装了不带${}和动态标签的sql信息，并提供对他们的处理接口
 * @author : Just wyx
 * @Date : 2020/8/4
 */
public class RawSqlSource implements SqlSource {
	// 一个静态的sqlSource
	private SqlSource sqlSource;

	public RawSqlSource(SqlNode mixedSqlNode) {
		// 真正处理#{}的逻辑要放在该构造方法中
		// 把处理之后的结果,封装成一个静态的sqlSource

		// 1.处理所有的sql节点，获取合并之后的完整的sql语句
		DynamicContext context = new DynamicContext(null);
		mixedSqlNode.apply(context);
		String sqlText = context.getSql();
		// 2.调用sqlsource的处理逻辑，对于#{}进行处理
		// 处理#{}
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
		String sql = tokenParser.parse(sqlText);

		sqlSource = new StaticSqlSource(sql,tokenHandler.getParameterMappings());
	}

	@Override
	public BoundSql getBoundSql(Object param) {

		return sqlSource.getBoundSql(param);
	}
}
