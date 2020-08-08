package com.wyx.mybatis.v3.scripting.xmltags;

/**
 * @author : Just wyx
 * @Description : 静态sqlNode
 * @Date : 2020/8/8
 */
public class StaticTextSqlNode implements SqlNode{
	private final String text;

	public StaticTextSqlNode(String text) {
		this.text = text;
	}

	@Override
	public boolean apply(DynamicContext context) {
		/**
		 * 不包含${}的sqlText,不需要关注#{}，简单的拼接到DynamicContext中
		 */
		context.appendSql(text);
		return true;
	}
}
