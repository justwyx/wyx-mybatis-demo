package com.wyx.mybatis.v2;

import com.wyx.mybatis.v2.entity.User;
import com.wyx.mybatis.v2.mapping.MappedStatement;
import com.wyx.mybatis.v2.mapping.ParameterMapping;
import com.wyx.mybatis.v2.mapping.SqlSource;
import com.wyx.mybatis.v2.scripting.defaults.RawSqlSource;
import com.wyx.mybatis.v2.scripting.xmltags.*;
import com.wyx.mybatis.v2.session.Configuration;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Description :
 * 1.properties配置文件升级为XML配置文件
 * 2.使用面向过程思维去优化代码
 * 3.使用面向对象思维去理解配置文件封装的类的作用
 * @author : Just wyx
 * @Date : 2020/8/5
 */
@SuppressWarnings("all")
public class TestV2 {

	private Configuration configuration;

	private String namespace;

	private boolean isDynamic = false;

	@Test
	public void testV2() {
		// 加载xml文件（全局配置文件及SQL映射文件）
		loadXML("mybatis-config.xml");

		// 执行查询
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("sex", 2);
		paramMap.put("username", "王五");
		List<User> userList = getResultList("test.queryUserByParams", paramMap);
		System.out.println(userList);
	}
	private void loadXML(String location) {
		configuration = new Configuration();
		// 获取全局配置文件对应的流对象
		InputStream is = getResourceAsStream(location);
		// 获取 document对象
		Document document = getDocument(is);
		// 根据xml语义解析
		parseConfiguration(document.getRootElement());
	}

	/**
	 * 根据全限定名获取Class对象
	 * @param parameterType
	 * @return
	 */
	private Class<?> resolveType(String parameterType) {
		try {
			Class<?> clazz = Class.forName(parameterType);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文件文档信息,By文件流对象
	 */
	private Document getDocument(InputStream is) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(is);
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取文件流对象，By文件路径
	 */
	private InputStream getResourceAsStream(String location) {
		return this.getClass().getClassLoader().getResourceAsStream(location);
	}

	/**
	 * 解析 mybatis-config文件信息
	 */
	private void parseConfiguration(Element rootElement) {
		Element environments = rootElement.element("environments");
		parseEnvironments(environments);
		Element mappers = rootElement.element("mappers");
		parseMappers(mappers);
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
			is = getResourceAsStream(resource);
			// 将流对象，转换成document对象
			document = getDocument(is);
			// 针对Document对象，按照mybatis的语义去解析Document
			parseMapper(document.getRootElement());
		}
	}

	/**
	 * 解析mapper文件
	 */
	private void parseMapper(Element rootElement) {
		// 获取namespace,与statement_id组成唯一id
		namespace = rootElement.attributeValue("namespace");
		// 获取所有select标签
		List<Element> selectElementList = rootElement.elements("select");
		for (Element selectElement : selectElementList) {
			parseSelectStatementElement(selectElement);
		}

		// todo 可能还有其它标签
	}

	/**
	 * 解析映射文件中的select标签
	 */
	private void parseSelectStatementElement(Element selectElement) {
		// 获取标签id
		String statementId = selectElement.attributeValue("id");
		if (statementId == null || statementId == "") {
			// 正常情况下，没有id应该抛出异常
			return;
		}
		// statementId = namespace + "." + CRUD标签的id属性,表示唯一id
		statementId = namespace + "." + statementId;

		String resultType = selectElement.attributeValue("resultType");
		Class<?> resultClass = resolveType(resultType);

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
		return parseScriptNode(selectElement);
	}

	private SqlSource parseScriptNode(Element selectElement) {
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

					sqlNodeList.add(new IfSqlNode(sqlNode, test));
				} else {
					// TODO 其它标签
				}


			} else {
				// TODO
			}

		}
		return new MixedSqlNode(sqlNodeList);
	}

	public <T> List<T> getResultList(String statementId, Object param) {
		// 声明返回对象
		List<T> resultList = new ArrayList<>();
		// 连接对象
		Connection connection;
		// 预处理对象
		Statement statement = null;
		// 结果集对象
		ResultSet resultSet = null;
		try {
			// 通过连接池获取数据库连接
			connection = configuration.getDataSource().getConnection();
			// 获取mappedStatement对象
			MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
			// 获取sql,此时的sql已经${}替换
			String sql = mappedStatement.getSqlSource().getBoundSql(param).getSql();
			// 创建 statement
			statement = createStatement(mappedStatement, sql, connection);
			// 设置参数
			setParameters(param, statement, mappedStatement.getSqlSource().getBoundSql(param).getParameterMappings());
			// 执行Statement
			resultSet = executeQuery(statement);
			// 获取结果
			handleResult(resultSet, mappedStatement, resultList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/**
			 * 释放资源,由于connection现在连接池管理，不需要手动释放
			 * 其它资源继续逆向释放
			 */
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
		}
		return resultList;
	}

	private Statement createStatement(MappedStatement mappedStatement, String sql, Connection connection) throws SQLException {
		String statementType = mappedStatement.getStatementType();
		if ("prepared".equals(statementType)) {
			return connection.prepareStatement(sql);
		} else {
			// todo 其它类型
		}
		return null;
	}

	private void setParameters(Object param, Statement statement,List<ParameterMapping> list) throws SQLException {
		if (statement instanceof PreparedStatement) {
			PreparedStatement preparedStatement = (PreparedStatement) statement;
			if (param instanceof Integer || param instanceof String) {
				// 设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
				preparedStatement.setObject(1, param);
			} else if (param instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) param;

				// 解析#{}之后封装的参数集合List<ParameterMapping>
				for (int i = 0; i < list.size(); i++) {
					ParameterMapping parameterMapping = list.get(i);
					String name = parameterMapping.getProperty();
					Object value = map.get(name);
					// 给map集合中的参数赋值
					preparedStatement.setObject(i + 1, value);
				}

			} else {
				//
			}
		} else {
			// todo
		}

	}

	private ResultSet executeQuery(Statement statement) throws SQLException {

		if (statement instanceof PreparedStatement) {
			PreparedStatement preparedStatement = (PreparedStatement) statement;
			return preparedStatement.executeQuery();
		}

		return null;
	}

	private <T> void handleResult(ResultSet resultSet, MappedStatement mappedStatement, List<T> resultList) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
		Class clazz = mappedStatement.getResultTypeClass();

		Object result;
		while (resultSet.next()) {
			result = clazz.newInstance();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				String columnName = metaData.getColumnName(i + 1);

				// 通过反射给指点列对应的属性名称赋值
				// 列名和属性名要一致
				Field field = clazz.getDeclaredField(columnName);
				// 暴力破解破坏封装
				field.setAccessible(true);
				field.set(result, resultSet.getObject(columnName));
			}
			resultList.add((T) result);
		}
	}

}
