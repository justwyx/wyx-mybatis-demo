package com.wyx.mybatis.v2.scripting.xmltags;

import com.wyx.mybatis.v2.utils.OgnlUtils;

/**
 * @Description : 记录的是if标签的sqlNode
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class IfSqlNode implements SqlNode{
	// 记录的是if标签中，test的值，OGNL表达示
	private final String text;
	// 记录的是 MixedSqlNode对象，面向接口编程，统一处理
	private final SqlNode mixedSqlNode;

	public IfSqlNode(SqlNode contents, String text) {
		this.text = text;
		this.mixedSqlNode = contents;
	}

	@Override
	public boolean apply(DynamicContext context) {
		// 使用OGNL对test中的内容进行判断（test属性值写的就是ONGL表达式的语法）
		Object parameter = context.getBindings().get("_parameter");
		// 只有满足条件后，才继续往下执行
		if (OgnlUtils.evaluateBoolean(text, parameter)){
			mixedSqlNode.apply(context);
			return true;
		}
		return false;
	}
}
