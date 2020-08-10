package com.wyx.mybatis.v3.builder.xml;

import com.wyx.mybatis.v3.builder.BaseBuilder;
import com.wyx.mybatis.v3.io.Resources;
import com.wyx.mybatis.v3.session.Configuration;
import com.wyx.mybatis.v3.utils.DocumentUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author : Just wyx
 * @Description : 用于解析全局配置文件
 * @Date : 2020/8/9
 */
public class XMLConfigBuilder extends BaseBuilder {

	public XMLConfigBuilder() {
		super(new Configuration());
	}

	public Configuration parse(Element rootElement) {
		Element environments = rootElement.element("environments");
		parseEnvironments(environments);
		Element mappers = rootElement.element("mappers");
		parseMappers(mappers);

		return configuration;
	}

	/**
	 * 解析 Environments 配置环境信息
	 */
	private void parseEnvironments(Element environments) {
		// 默认环境配置
		String aDefault = environments.attributeValue("default");
		// 获取所有环境信息
		List<Element> elements = environments.elements("environment");
		for (Element element : elements) {
			// 解析的环境的默认环境相匹配，则继续解析
			if (!aDefault.equals(element.attributeValue("id"))) {
				continue;
			}
			parseDataSource(element.element("dataSource"));
		}
	}

	/**
	 * 解析dataSource对象
	 */
	private void parseDataSource(Element dataSource) {
		String type = dataSource.attributeValue("type");
		if (type.equals("DBCP")) {
			Properties properties = parseProperties(dataSource);
			// 构建连接池对象
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName(properties.getProperty("db.driver"));
			ds.setUrl(properties.getProperty("db.url"));
			ds.setUsername(properties.getProperty("db.username"));
			ds.setPassword(properties.getProperty("db.password"));
			configuration.setDataSource(ds);
		}
	}

	/**
	 * 将连接属性解析到properties文件中
	 */
	private Properties parseProperties(Element dataSource) {
		Properties properties = new Properties();
		// 获取所有properties对象
		List<Element> list = dataSource.elements("property");
		// 解析值内容
		for (Element element : list) {
			properties.put(element.attributeValue("name"), element.attributeValue("value"));
		}
		return properties;
	}

	private void parseMappers(Element mappers) {
		// 获取所有配置文件信息
		List<Element> mapperList = mappers.elements("mapper");
		String resource;
		InputStream is;
		Document document;
		for (Element element : mapperList) {
			resource = element.attributeValue("resource");
			// 根据xml的路径，获取对应的输入流
			is = Resources.getResourceAsStream(resource);
			// 将流对象，转换成document对象
			document = DocumentUtils.getDocument(is);
			// 针对Document对象，按照mybatis的语义去解析Document

			XMLMapperBuilder mapperParser = new XMLMapperBuilder(configuration);
			mapperParser.parse(document.getRootElement());
		}
	}


}
