package com.wyx.jdbc.sqlnode;

import com.wyx.jdbc.framework.config.DynamicContext;
import com.wyx.jdbc.utils.OgnlUtils;

/**
 * @Description :
 * 封装了带有if标签的动态标签
 * @author : Just wyx
 * @Date : 2020/8/4
 */
public class IfSqlNode implements SqlNode{
	private String text;

	private SqlNode mixedSqlNode;

	public IfSqlNode(String text, SqlNode mixedSqlNode) {
		this.text = text;
		this.mixedSqlNode = mixedSqlNode;
	}

	@Override
	public void apply(DynamicContext context) {
		// 使用OGNL对test中的内容进行判断（test属性值写的就是ONGL表达式的语法）
		Object parameter = context.getBindings().get("_parameter");
		// 只有满足条件后，才继续往下执行
		if (OgnlUtils.evaluateBoolean(text, parameter)){
			mixedSqlNode.apply(context);
		}
	}
}
