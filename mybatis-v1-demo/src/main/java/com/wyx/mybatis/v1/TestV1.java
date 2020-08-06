package com.wyx.mybatis.v1;

import com.wyx.mybatis.v1.entity.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Description :
 * 		解决硬编码问题（properties文件）
 *      properties文件中的内容，最终会被【加载】到Properties集合中
 * @author : Just wyx
 * @Date : 2020/8/5
 */
@SuppressWarnings("all")
public class TestV1 {
	/**
	 * 保存jdbc配置信息
	 */
	private Properties jdbcProperties;

	/**
	 * 连接池对象
 	 */
	private BasicDataSource basicDataSource;

	@Test
	public void testV1() throws IOException {
		// 加载配置文件
		loadProperties("jdbc.properties");
		// 初始化数据库连接池
		initJdbcDataSource();

		// 执行查询
		List<User> resultList1 = getResultList("queryById", 1);
		System.out.println("resultList1: " + resultList1);

		// 执行查询
		List<User> resultList2 = getResultList("queryByUsername", "王五");
		System.out.println("resultList2: " + resultList2);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 1);
		paramMap.put("username", "王五");
		List<User> resultList3 = getResultList("queryByIdAndUsername", paramMap);
		System.out.println("resultList3: " + resultList3);
	}

	/**
	 * 加载配置文件
	 */
	private void loadProperties(String location) throws IOException {
		// 初始化 jdbcProperties
		jdbcProperties = new Properties();
		// 将jdbc.properties以流的形式加载到properties
		jdbcProperties.load(this.getClass().getClassLoader().getResourceAsStream(location));
	}

	/**
	 * 初始化数据库连接池
	 */
	private void initJdbcDataSource() {
		basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(jdbcProperties.getProperty("db.driver"));
		basicDataSource.setUrl(jdbcProperties.getProperty("db.url"));
		basicDataSource.setUsername(jdbcProperties.getProperty("db.username"));
		basicDataSource.setPassword(jdbcProperties.getProperty("db.password"));
	}

	/**
	 * 封装统一执行方法
	 */
	private <T> List<T> getResultList(String statementId, Object param) {
		// 声明返回对象
		List<T> resultList = new ArrayList<>();
		// 连接对象
		Connection connection;
		// 预处理对象
		PreparedStatement statement = null;
		// 结果集对象
		ResultSet resultSet = null;
		try {
			// 通过连接池获取数据库连接
			connection = basicDataSource.getConnection();
			// 获取sql
			String sql = jdbcProperties.getProperty("db.sql." + statementId);
			// 进行预编译
			statement = connection.prepareStatement(sql);
			// 参数赋值
			if (param instanceof Integer || param instanceof String) {
				statement.setObject(1, param);
			} else if (param instanceof Map) {
				Map<String, Object> paramMap = (Map)param;
				// 获取入参顺序
				String columnnames = jdbcProperties.getProperty("db.sql." + statementId + ".columnnames");
				String[] columnnameArray = columnnames.split(",");
				for (int i = 0; i < columnnameArray.length; i++) {
					statement.setObject(i + 1, paramMap.get(columnnameArray[i]));
				}
			} else {
				// todo 其它参数类型暂未处理
			}
			// 执行sql，获取返回结果
			resultSet = statement.executeQuery();
			// 获取返回的结果集对象
			String resultType = jdbcProperties.getProperty("db.sql." + statementId + ".resulttype");
			Class<?> clazz = Class.forName(resultType);
			// 单个结果对象
			Object result;
			// 循环为结果集对象赋值
			while (resultSet.next()) {
				result = clazz.newInstance();
				// 获取结果对象
				ResultSetMetaData metaData = resultSet.getMetaData();
				// 获取结果列总数
				int columnCount = metaData.getColumnCount();
				String columnName;
				Field field;
				for (int i = 0; i < columnCount; i++) {
					// 获取列名，下标是从1开始的
					columnName = metaData.getColumnName(i + 1);
					// 根据列名获取字段对象
					field = clazz.getDeclaredField(columnName);
					// 暴力破解破坏封装
					field.setAccessible(true);
					// 字段赋值
					field.set(result, resultSet.getObject(columnName));
				}
				resultList.add((T)result);
			}
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
}
