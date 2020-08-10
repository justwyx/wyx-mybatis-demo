package com.wyx.mybatis.v1;

import com.wyx.mybatis.v1.entity.User;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @Description : 将连接信息与sql信息通过properties文件保存,解决硬编码问题
 * properties文件中的内容，最终会被【加载】到Properties集合中
 * @author : Just wyx
 * @Date : 2020/8/5
 */
@SuppressWarnings("all")
public class TestV1 {
	/**
	 * 保存全局jdbc配置信息
	 */
	private Properties jdbcProperties;
	/**
	 * 数据库连接池对象
 	 */
	private BasicDataSource basicDataSource;

	/**
	 * 启动初始化
	 */
	@Before
	public void init() throws IOException {
		// 加载配置文件
		loadProperties("jdbc.properties");
		// 初始化数据库连接池
		initJdbcDataSource();
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
	 *v1版本问题：
	 * 配置文件问题，property不太好表述，不方便维护动态sql
	 *
	 *v1优化方案：
	 * 1.property升级成xml(增加对xml的解析)
	 * 2.使用面向过程的方式优化代码
	 * 3.使用面向对象思维去理解配置文件封装的类的作用
	 *
	 * 参照真正mybatis源码：
	 * 将运行时的信息封装到全局配置文件中(连接信息，映射文件位置信息等)
	 * 将需求有关的信息封装到mapper映射文件中
	 *
	 */
	@Test
	public void testV1() throws IOException {
		// 执行查询
		List<User> resultList = selectList("query", null);
		System.out.println("resultList: " + resultList);

		// 执行查询
		List<User> resultList1 = selectList("queryById", 1);
		System.out.println("resultList1: " + resultList1);

		// 执行查询
		List<User> resultList2 = selectList("queryByUsername", "李四");
		System.out.println("resultList2: " + resultList2);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 4);
		paramMap.put("username", "李四");
		List<User> resultList3 = selectList("queryByIdAndUsername", paramMap);
		System.out.println("resultList3: " + resultList3);
	}

	/**
	 * 封装统一获取结果集方法
	 */
	private <T> List<T> selectList(String statementId, Object param) {
		// 声明返回对象
		List<T> resultList = new ArrayList<>();
		// 预处理对象
		PreparedStatement statement = null;
		// 结果集对象
		ResultSet resultSet = null;
		try {
			// 通过连接池获取数据库连接
			Connection connection = basicDataSource.getConnection();
			// 获取sql语句，?表示占位符
			// 获取到的sql语句，例:select * from user where id = ?
			String sql = jdbcProperties.getProperty("db.sql." + statementId);
			// 使用预处理对象对sql语句进行预处理
			statement = connection.prepareStatement(sql);
			// 没有入参不做赋值操作
			if (param != null) {
				// 参数赋值，针对不同的入参进行不同处理
				if (param instanceof Integer || param instanceof String) {
					// 简单类型不需要关注参数名称
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
					// todo 其它参数类型暂未处理或者抛出不支付数据类型异常
				}
			}
			// 执行并取返回结果
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
