package com.wyx.mybatis.v2.scripting.xmltags;

import com.wyx.mybatis.v2.utils.GenericTokenParser;
import com.wyx.mybatis.v2.utils.OgnlUtils;
import com.wyx.mybatis.v2.utils.SimpleTypeRegistry;
import com.wyx.mybatis.v2.utils.TokenHandler;

/**
 * @Description : 封装了带有${}的sql文本
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class TextSqlNode implements SqlNode{
	/**
	 * 保存的的未处理过的sqlText
	 */
	private final String text;

	public TextSqlNode(String text) {
		this.text = text;
	}

	@Override
	public boolean apply(DynamicContext context) {
		// 处理${}
		GenericTokenParser parser = new GenericTokenParser("${","}",new BindingTokenHandler(context));
		context.appendSql(parser.parse(text));
		return true;
	}

	/**
	 * 判断文本中是否包含${},true:包含
	 */
	public boolean isDynamic() {
		return text.indexOf("${") > -1;
	}

	class BindingTokenHandler implements TokenHandler {
		// 为了取得入参对象
		private final DynamicContext context;

		public BindingTokenHandler(DynamicContext context) {
			this.context = context;
		}

		@Override
		public String handleToken(String content) {
			Object parameter = context.getBindings().get(DynamicContext.PARAMETER_OBJECT_KEY);
			// 校验参数是否为简单参数类型
			if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
				return parameter.toString();
			}
			Object value = OgnlUtils.getValue(content, parameter);
			return value == null ? "" : value.toString();
		}
	}
}
