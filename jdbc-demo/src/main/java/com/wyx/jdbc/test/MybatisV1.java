package com.wyx.jdbc.test;

import com.mysql.cj.util.StringUtils;
import com.wyx.jdbc.entity.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Description : 解决硬编码问题（properties文件）
 * properties文件中的内容，最终会被【加载】到Properties集合中
 * @author : Just wyx
 * @Date : 2020/8/1
 */
public class MybatisV1 {
	private Properties properties = new Properties();

	@Test
	public void jdbc() {
		// 加载配制文件
		loadProperties("jdbc.properties");

		// 执行查询
		List<User> userList = getData("queryById", 1);
//		List<User> userList = getData("queryByUsername", "王五");

//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("id", 1);
//		paramMap.put("username", "王五");
//		List<User> userList = getData("queryByIdAndUsername", paramMap);


		System.out.println(userList);
	}

	/**
	 * 加载配置文件
	 */
	public void loadProperties(String location) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(location);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抽取一个通用的方法获取数据
	 */
	public <T> List<T> getData(String statementId, Object param) {
		List<T> resultList = new ArrayList<T>();
		// 数据库连接
		Connection connection;
		// 预编译的Statement，使用预编译的Statement提高数据库性能
		PreparedStatement preparedStatement = null;
		// 结果集
		ResultSet resultSet = null;

		try {
			// 解决连接获取时的硬编码问题和频繁连接的问题
			BasicDataSource basicDataSource = new BasicDataSource();
			basicDataSource.setDriverClassName(properties.getProperty("db.driver"));
			basicDataSource.setUrl(properties.getProperty("db.url"));
			basicDataSource.setUsername(properties.getProperty("db.username"));
			basicDataSource.setPassword(properties.getProperty("db.password"));
			// 通过连接池获取数据库连接
			connection = basicDataSource.getConnection();


			// 获取sql语句
			String sql = properties.getProperty("db.sql." + statementId);
			// 获取预处理statement
			preparedStatement = connection.prepareStatement(sql);
			if (param instanceof Integer || param instanceof String) {
				// 设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
				preparedStatement.setObject(1, param);
			} else if (param instanceof Map) {
				Map<String ,Object> map = (Map<String, Object>) param;
				String columnnames = properties.getProperty("db.sql." + statementId + ".columnnames");
				if (!StringUtils.isNullOrEmpty(columnnames) && columnnames.length() > 0) {
					String[] columnnameArray = columnnames.split(",");
					for (int i = 0; i < columnnameArray.length; i++) {
						preparedStatement.setObject(i + 1, map.get(columnnameArray[i]));
					}
				}

			} else {
				throw new Exception("param不支持");
			}

			// 向数据库发出sql执行查询，查询出结果集
			resultSet = preparedStatement.executeQuery();

			// 获取结果集
			String resultType = properties.getProperty("db.sql." + statementId + ".resulttype");
			Class<?> clazz = Class.forName(resultType);

			// 遍历查询结果集
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
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}


}
