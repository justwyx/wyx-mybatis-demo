package com.wyx.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * @Description : TODO 2020/7/25
 * @author : Just wyx
 * @Date : 2020/7/25
 */
public class JdbcTest {

	@Test
	public void jdbcTest() {
		Connection connection = null;

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		try {
			// 加载驱动,jdk1.6之后也可以不加载
//			Class.forName("com.mysql.cj.jdbc.Driver");
			// 创建连接
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wyx_mybatis_demo?characterEncoding=utf-8", "root", "root");
			// 定义sql语句,?表示占位符
			String sql = "select * from user where username = ?";
			// 预处理
			statement = connection.prepareStatement(sql);
			// 设置参数,补全sql入参,第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
			statement.setObject(1, "王五");
			// 执行，获取结果集
			resultSet = statement.executeQuery();
			// 输出结果集
			while (resultSet.next()) {
				System.out.println("id:" + resultSet.getObject("id") + ",username:" + resultSet.getObject("username"));
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
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
