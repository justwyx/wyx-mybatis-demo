package com.wyx.jdbc.sqlnode;

import com.wyx.jdbc.framework.config.DynamicContext;

/**
 * @author : Just wyx
 * @Description : 封装了不带有${}的SQL文本
 * @Date : 2020/8/4
 */
public class StaticTextSqlNode implements SqlNode{

	private String sqlText;

	public StaticTextSqlNode(String sqlText) {
		this.sqlText = sqlText;
	}

	@Override
	public void apply(DynamicContext context) {
		context.appendSql(sqlText);
	}
}
