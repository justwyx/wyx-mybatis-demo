package com.wyx.jdbc.sqlnode;

import com.wyx.jdbc.framework.config.DynamicContext;
import com.wyx.jdbc.utils.GenericTokenParser;
import com.wyx.jdbc.utils.OgnlUtils;
import com.wyx.jdbc.utils.SimpleTypeRegistry;
import com.wyx.jdbc.utils.TokenHandler;

/**
 * @Description : 封装了带有${}的sql文本
 * @author : Just wyx
 * @Date : 2020/8/4
 */
public class TextSqlNode implements SqlNode{

	private String sqlText;

	public TextSqlNode(String sqlText) {
		this.sqlText = sqlText;
	}

	@Override
	public void apply(DynamicContext context) {
		// 处理${}
		GenericTokenParser tokenParser = new GenericTokenParser("${","}",new BindingTokenHandler(context));
		String sql = tokenParser.parse(sqlText);
		context.appendSql(sql);
	}

	/**
	 * 判断文本中是否包含${},true:包含
	 */
	public boolean isDynamic() {
		return sqlText.indexOf("${") > -1;
	}

	class BindingTokenHandler implements TokenHandler {
		// 为了获取入参对象
		private DynamicContext context;

		public BindingTokenHandler(DynamicContext context) {
			this.context = context;
		}

		@Override
		public String handleToken(String content) {
			Object parameter = context.getBindings().get("_parameter");
			if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
				return parameter.toString();
			}
			// 使用ONGL表达式获取值
			Object value = OgnlUtils.getValue(content, parameter);
			return value == null ? "": value.toString();
		}
	}
}
