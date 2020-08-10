package com.wyx.mybatis.v3.builder.xml;

import com.wyx.mybatis.v3.builder.BaseBuilder;
import com.wyx.mybatis.v3.session.Configuration;
import org.dom4j.Element;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : 用于处理mapper映射文件
 * @Date : 2020/8/9
 */
public class XMLMapperBuilder extends BaseBuilder {

	public XMLMapperBuilder(Configuration configuration) {
		super(configuration);
	}

	public void parse(Element rootElement) {
		// 获取namespace,与statement_id组成唯一id
		String namespace = rootElement.attributeValue("namespace");
		// todo 可能还有其它标签
		// 获取所有select标签
		List<Element> selectElementList = rootElement.elements("select");
		for (Element selectElement : selectElementList) {
			XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration);
			statementParser.parseStatement(selectElement, namespace);
		}


	}
}
