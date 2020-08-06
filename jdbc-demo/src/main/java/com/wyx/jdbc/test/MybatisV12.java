//package com.wyx.jdbc.test;
//
//import com.wyx.jdbc.entity.User;
//import org.apache.commons.dbcp.BasicDataSource;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.sql.*;
//import java.util.*;
//
///**
// * @Description :  mybatisV1版本
// * 主要解决硬编码问题，通过properties文件
// * @author : Just wyx
// * @Date : 2020/8/4
// */
//public class MybatisV12 {
//	private Properties properties;
//
//	/**
//	 * mybatisV1版本测试
//	 */
//	@Test
//	public void test() throws IOException {
//		// 加载properties文件
//		loadProperties("jdbc.properties");
//
//
//		// 执行查询
//		List<User> resultList1 = getResultList("queryById", 1);
//		System.out.println("resultList1: " + resultList1);
//
//		// 执行查询
//		List<User> resultList2 = getResultList("queryByUsername", "王五");
//		System.out.println("resultList2: " + resultList2);
//
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("id", 1);
//		paramMap.put("username", "王五");
//		List<User> resultList3 = getResultList("queryByIdAndUsername", paramMap);
//		System.out.println("resultList3: " + resultList3);
//	}
//
//	/**
//	 * (what) : 加载jdbc配置文件
//	 * (why) :
//	 * (how) :
//	 * @param location 入参
//	 * @Author : Just wyx
//	 * @Date : 10:19 2020/8/4
//	 * @return : void
//	 */
//	private void loadProperties(String location) throws IOException {
//		InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
//		properties = new Properties();
//		properties.load(is);
//	}
//
//	/**
//	 * (what) : 封装一个统一的方法进行连接获取数据
//	 * (why) :
//	 * (how) :
//	 * @param statementId
//	 * @param param 入参
//	 * @Author : Just wyx
//	 * @Date : 10:22 2020/8/4
//	 * @return : java.util.List<T>
//	 */
//	public <T> List<T> getResultList(String statementId, Object param) {
//		// 返回结果集对象
//		List<T> resultList = new ArrayList<T>();
//		// 连接对象
//		Connection connection;
//		// 预处理对象
//		PreparedStatement statement = null;
//		// 结果集对象
//		ResultSet resultSet = null;
//
//		// 解决连接获取时的硬编码问题和频繁连接的问题
//		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setDriverClassName(properties.getProperty("db.driver"));
//		basicDataSource.setUrl(properties.getProperty("db.url"));
//		basicDataSource.setUsername(properties.getProperty("db.username"));
//		basicDataSource.setPassword(properties.getProperty("db.password"));
//
//		try {
//			// 通过连接池获取数据库连接
//			connection = basicDataSource.getConnection();
//
//			// 获取sql
//			String sql = properties.getProperty("db.sql." + statementId);
//			// 进行预编译
//			statement = connection.prepareStatement(sql);
//			// 参数赋值
//			if (param instanceof Integer || param instanceof String) {
//				statement.setObject(1, param);
//			} else if (param instanceof Map) {
//				Map<String, Object> paramMap = (Map)param;
//				// 获取入参顺序
//				String columnnames = properties.getProperty("db.sql." + statementId + ".columnnames");
//				String[] columnnameArray = columnnames.split(",");
//				for (int i = 0; i < columnnameArray.length; i++) {
//					statement.setObject(i + 1, paramMap.get(columnnameArray[i]));
//				}
//			} else {
//				// todo
//			}
//
//			// 执行sql，获取返回结果
//			resultSet = statement.executeQuery();
//
//			// 获取返回的结果集对象
//			String resultType = properties.getProperty("db.sql." + statementId + ".resulttype");
//			Class<?> clazz = Class.forName(resultType);
//
//			// 单个结果对象
//			Object result;
//			// 循环为结果集对象赋值
//			while (resultSet.next()) {
//				result = clazz.newInstance();
//				// 获取结果对象
//				ResultSetMetaData metaData = resultSet.getMetaData();
//				// 获取结果列总数
//				int columnCount = metaData.getColumnCount();
//				String columnName;
//				Field field;
//				for (int i = 0; i < columnCount; i++) {
//					// 获取列名，下标是从1开始的
//					columnName = metaData.getColumnName(i + 1);
//					// 根据列名获取字段对象
//					field = clazz.getDeclaredField(columnName);
//					// 暴力破解破坏封装
//					field.setAccessible(true);
//					// 字段赋值
//					field.set(result, resultSet.getObject(columnName));
//				}
//				resultList.add((T)result);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			//释放资源,由于connection现在连接池管理，不需要手动释放
//			if (resultSet != null) {
//				try {
//					resultSet.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return resultList;
//	}
//}
