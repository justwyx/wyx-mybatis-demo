package com.wyx.mybatis.v3.scripting.xmltags;

import com.wyx.mybatis.v3.utils.OgnlUtils;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/8
 * @Date : 2020/8/8
 */
public class IfSqlNode implements SqlNode{
	/**
	 * 保存的是if结点test信息
	 */
	private final String test;

	private final SqlNode contents;

	public IfSqlNode(String test, SqlNode contents) {
		this.test = test;
		this.contents = contents;
	}

	@Override
	public boolean apply(DynamicContext context) {
		// 使用OGNL对test中的内容进行判断（test属性值写的就是ONGL表达式的语法）
		Object parameter = context.getBindings().get("_parameter");
		// 只有满足条件后，才继续往下执行
		if (OgnlUtils.evaluateBoolean(test, parameter)){
			contents.apply(context);
			return true;
		}
		return false;
	}
}
