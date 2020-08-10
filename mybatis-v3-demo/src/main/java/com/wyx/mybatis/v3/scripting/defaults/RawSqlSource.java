package com.wyx.mybatis.v3.scripting.defaults;

import com.wyx.mybatis.v3.builder.SqlSourceBuilder;
import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.SqlSource;
import com.wyx.mybatis.v3.scripting.xmltags.DynamicContext;
import com.wyx.mybatis.v3.scripting.xmltags.SqlNode;

/**
 * @author : Just wyx
 * @Description : 封装的是不包含${}和动态标签的sql
 * @Date : 2020/8/8
 */
public class RawSqlSource implements SqlSource {

	/**
	 * 一个静态的sqlSource
	 */
	private final SqlSource sqlSource;

	/**
	 * 由于不包含${}与动态sql语句，只有第一次加载的时候需要创建一个静态的sqlSource对象，后续不需要在解析
	 */
	public RawSqlSource(SqlNode mixedSqlNode) {
		// 没有${}和动态标签,不需要根据入参进行解析
		DynamicContext context = new DynamicContext(null);
		// 将sqlNode中的sql进行组装
		mixedSqlNode.apply(context);
		// 获取原始Sql，此时的sqlText可能还包含#{}
		String originalSql = context.getSql();
		// 处理#{}
		SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder();
		// 封装成静态sql对象
		sqlSource = sqlSourceParser.parse(originalSql);
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		// 去调用静态sqlSource的getBoundSql
		return sqlSource.getBoundSql(parameterObject);
	}
}
