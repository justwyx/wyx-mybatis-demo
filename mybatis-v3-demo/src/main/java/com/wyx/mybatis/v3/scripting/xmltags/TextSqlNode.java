package com.wyx.mybatis.v3.scripting.xmltags;

import com.wyx.mybatis.v3.utils.GenericTokenParser;
import com.wyx.mybatis.v3.utils.OgnlUtils;
import com.wyx.mybatis.v3.utils.SimpleTypeRegistry;
import com.wyx.mybatis.v3.utils.TokenHandler;

/**
 * @author : Just wyx
 * @Description : 封装带了${}的sql文本
 * @Date : 2020/8/8
 */
public class TextSqlNode implements SqlNode{
	private final String text;

	public TextSqlNode(String text) {
		this.text = text;
	}

	/**
	 * 判断sql文本中是否包含${}
	 */
	public boolean isDynamic() {
		return text.indexOf("${") > -1;
	}

	@Override
	public boolean apply(DynamicContext context) {
		/**
		 * 处理掉sql文本中的${}，在将sql拼接到DynamicContext中
		 */
		GenericTokenParser parser = new GenericTokenParser("${","}", new BindingTokenHandler(context));
		context.appendSql(parser.parse(text));
		return true;
	}

	class BindingTokenHandler implements TokenHandler {
		// 为了获取参数对象
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
