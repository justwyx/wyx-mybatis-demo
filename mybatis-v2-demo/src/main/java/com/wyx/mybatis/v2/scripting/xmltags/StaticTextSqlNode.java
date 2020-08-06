package com.wyx.mybatis.v2.scripting.xmltags;

/**
 * @Description : 静态的sqlNode对象，封装的是已处理好的sql对象
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class StaticTextSqlNode implements SqlNode{
	/**
	 * 记录的是sql语句
	 */
	private final String text;

	/**
	 * 初始化时赋值，不提供get set方法
	 */
	public StaticTextSqlNode(String text) {
		this.text = text;
	}

	@Override
	public boolean apply(DynamicContext context) {
		// 由于是已处理的sqlText,只需要进行简单的拼接
		context.appendSql(text);
		return true;
	}
}
