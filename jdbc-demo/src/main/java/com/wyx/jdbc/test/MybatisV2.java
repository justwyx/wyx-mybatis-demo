package com.wyx.jdbc.test;

import com.wyx.jdbc.entity.User;
import com.wyx.jdbc.framework.config.Configuratoin;
import com.wyx.jdbc.framework.config.MappedStatement;
import com.wyx.jdbc.framework.config.ParameterMapping;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;


/**
 * @author : Just wyx
 * @Description :
 * 1.properties配置文件升级为XML配置文件
 * 2.使用面向过程思维去优化代码
 * 3.使用面向对象思维去理解配置文件封装的类的作用
 * @Date : 2020/8/1
 */
public class MybatisV2 {
	private Configuratoin configuration;

	@Test
	public void test() {
		// 加载xml文件（全局配置文件及SQL映射文件）
		loadXML("mybatis-config.xml");

		// 执行查询
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 1);
		paramMap.put("username", "王五");
		List<User> userList = getData("queryByIdAndUsername", paramMap);
		System.out.println(userList);
	}

	private void loadXML(String location) {
		configuration = new Configuratoin();
		// todo 解析xml文件,最终将信息封装到configuration对象中
		// 获取全局配置文件对应的流对象
		InputStream is = getInputStreamAsResource(location);
		// 获取 document对象
		Document document = getDocument(is);
		// 根据xml语义解析
		parseConfiguration(document.getRootElement());

	}

	private void parseConfiguration(Element rootElement) {
		Element environments = rootElement.element("environments");
		parseEnvironments(environments);
		Element mappers = rootElement.element("mappers");
		parseMappers(mappers);
	}

	private void parseMappers(Element mappers) {
		//TODO
	}

	private void parseEnvironments(Element environments) {
		String aDefault = environments.attributeValue("default");
		List<Element> elements = environments.elements("environment");
		for (Element element : elements) {
			String id = element.attributeValue("id");
			if (aDefault.equals(id)) {
				parseDataSource(element.element("dataSource"));
			}
		}
	}

	private void parseDataSource(Element dataSource) {
		String type = dataSource.attributeValue("type");
		if (type.equals("DBCP")) {
			BasicDataSource ds = new BasicDataSource();
			Properties properties = parseProperties(dataSource);
			ds.setDriverClassName(properties.getProperty("db.driver"));
			ds.setUrl(properties.getProperty("db.url"));
			ds.setUsername(properties.getProperty("db.username"));
			ds.setPassword(properties.getProperty("db.password"));
			configuration.setDataSource(ds);
		}
	}

	private Properties parseProperties(Element dataSource) {
		Properties properties = new Properties();
		List<Element> list = dataSource.elements("property");
		for (Element element : list) {
			String name = element.attributeValue("name");
			String value = element.attributeValue("value");
			properties.put(name, value);
		}
		return properties;
	}

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

	private InputStream getInputStreamAsResource(String location) {
		return this.getClass().getClassLoader().getResourceAsStream(location);
	}

	public <T> List<T> getData(String statementId, Object param) {
		List<T> resultList = new ArrayList<T>();
		// 数据库连接
		Connection connection;
		// 预编译的Statement，使用预编译的Statement提高数据库性能
		Statement statement = null;
		// 结果集
		ResultSet resultSet = null;

		try {
			// 连接获取
			MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
			connection = getConnection();
			// sql获取
			String sql = getSql(mappedStatement);

			// 创建Statement
			statement = createStatement(mappedStatement, sql, connection);

			// 设置参数
			setParameters(param, statement, mappedStatement);

			// 执行Statement
			resultSet = executeQuery(statement);

			// 获取结果
			handleResult(resultSet, mappedStatement, resultList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//释放资源
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultList;
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

	private ResultSet executeQuery(Statement statement) throws SQLException {

		if (statement instanceof PreparedStatement) {
			PreparedStatement preparedStatement = (PreparedStatement) statement;
			return preparedStatement.executeQuery();
		}

		return null;
	}

	private void setParameters(Object param, Statement statement, MappedStatement mappedStatement) throws SQLException {
		if (statement instanceof PreparedStatement) {
			PreparedStatement preparedStatement = (PreparedStatement) statement;
			if (param instanceof Integer || param instanceof String) {
				// 设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
				preparedStatement.setObject(1, param);
			} else if (param instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) param;

				// todo 需要解析#{}之后封装的参数集合List<ParameterMapping>
				List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
				for (int i = 0; i < parameterMappings.size(); i++) {
					ParameterMapping parameterMapping = parameterMappings.get(i);
					String name = parameterMapping.getName();
					Object value = map.get(name);
					// 给map集合中的参数赋值
					preparedStatement.setObject(i + 1, value);
				}

//				String columnnames = properties.getProperty("db.sql." + statementId + ".columnnames");
//				if (!StringUtils.isNullOrEmpty(columnnames) && columnnames.length() > 0) {
//					String[] columnnameArray = columnnames.split(",");
//					for (int i = 0; i < columnnameArray.length; i++) {
//						preparedStatement.setObject(i + 1, map.get(columnnameArray[i]));
//					}
//				}

			} else {
				//
			}
		} else {
			// todo
		}

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

	private String getSql(MappedStatement mappedStatement) throws SQLException {
		return null;
	}

	private Connection getConnection() throws SQLException {
		DataSource dataSource = configuration.getDataSource();
		return dataSource.getConnection();
	}

}
