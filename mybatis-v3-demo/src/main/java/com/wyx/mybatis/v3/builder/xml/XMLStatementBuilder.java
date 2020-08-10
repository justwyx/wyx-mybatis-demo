package com.wyx.mybatis.v3.builder.xml;

import com.wyx.mybatis.v3.builder.BaseBuilder;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.mapping.SqlSource;
import com.wyx.mybatis.v3.scripting.xmltags.*;
import com.wyx.mybatis.v3.session.Configuration;
import com.wyx.mybatis.v3.utils.ReflectUtil;
import org.dom4j.Element;

/**
 * @author : Just wyx
 * @Description : 用于解析statement标签
 * @Date : 2020/8/9
 */
public class XMLStatementBuilder extends BaseBuilder {

	public XMLStatementBuilder(Configuration configuration) {
		super(configuration);
	}

	public void parseStatement(Element selectElement, String namespace) {
		// 获取标签id
		String statementId = selectElement.attributeValue("id");
		if (statementId == null || statementId == "") {
			// 正常情况下，没有id应该抛出异常
			return;
		}
		// statementId = namespace + "." + CRUD标签的id属性,表示唯一id
		statementId = namespace + "." + statementId;

		String resultType = selectElement.attributeValue("resultType");
		Class<?> resultClass = ReflectUtil.resolveType(resultType);

		String statementType = selectElement.attributeValue("statementType");
		statementType = statementType == null || statementType == "" ? "prepared" : statementType;

		//SqlSource和SqlNode的封装过程
		SqlSource sqlSource = createSqlSource(selectElement);

		// 构建 MappedStatement 对象
		MappedStatement mappedStatement = new MappedStatement.Builder()
				.setStatementId(statementId)
				.setResultTypeClass(resultClass)
				.setStatementType(statementType)
				.setSqlSource(sqlSource)
				.build();
		configuration.addMappedStatement(statementId, mappedStatement);
	}

	/**
	 * SqlSource和SqlNode的封装过程
	 */
	private SqlSource createSqlSource(Element selectElement) {
		XMLScriptBuilder scriptParser = new XMLScriptBuilder();

		SqlSource sqlSource = scriptParser.parseScriptNode(selectElement);
		return sqlSource;
	}

}
