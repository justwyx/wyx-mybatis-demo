package com.wyx.mybatis.v3.scripting.xmltags;

import com.wyx.mybatis.v3.scripting.defaults.RawSqlSource;
import com.wyx.mybatis.v3.mapping.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : 用于解析sqlNode信息
 * @Date : 2020/8/9
 */
public class XMLScriptBuilder {
	private boolean isDynamic = false;

	public SqlSource parseScriptNode(Element selectElement) {
		// 解析所有sql节点，最终封装到mixedSqlNode中
		SqlNode mixedSqlNode = parseDynamicTags(selectElement);

		SqlSource sqlSource;
		//如果带有${}或者动态SQL标签
		if (isDynamic){
			sqlSource = new DynamicSqlSource(mixedSqlNode);
		}else{
			sqlSource = new RawSqlSource(mixedSqlNode);
		}
		// 重置
		isDynamic = false;
		return sqlSource;
	}

	private SqlNode parseDynamicTags(Element selectElement) {
		List<SqlNode> sqlNodeList = new ArrayList<>();
		// 获取select 标签的子元素:文本类型或者Element类型
		int nodeCount = selectElement.nodeCount();
		Node node;
		for (int i = 0; i < nodeCount; i++) {
			node = selectElement.node(i);
			// 如果节点是文本格式
			if (node instanceof Text){
				String text = node.getText();
				if (text == null) {
					continue;
				}
				// 去掉前后空格
				text = text.trim();
				if ("".equals(text)) {
					continue;
				}
				// 先将sql文本封装到textSqlNode文件中
				TextSqlNode textSqlNode = new TextSqlNode(text);
				if (textSqlNode.isDynamic()){
					sqlNodeList.add(textSqlNode);
					isDynamic = true;
				}else{
					sqlNodeList.add(new StaticTextSqlNode(text.trim()));
				}
			} else if (node instanceof Element) {
				// 如果是节点
				isDynamic = true;
				Element element = (Element) node;
				String name = element.getName();
				if ("if".equals(name)) {
					String test = element.attributeValue("test");
					//递归去解析子元素
					SqlNode sqlNode = parseDynamicTags(element);

					sqlNodeList.add(new IfSqlNode(test, sqlNode));
				} else {
					// TODO 其它标签
				}


			} else {
				// TODO
			}

		}
		return new MixedSqlNode(sqlNodeList);
	}
}
