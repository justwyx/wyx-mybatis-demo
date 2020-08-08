package com.wyx.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * @Description : jdbc操作连接数据库
 * @author : Just wyx
 * @Date : 2020/7/25
 */
public class JdbcTest {

	/**
	 * JDBC问题:
	 * 1.数据库连接创建，释放频繁造成系统资源浪费，从而影响系统性能
	 * 2.SQL语句在代码中硬编码，造成代码不易维护，实际应用中sql变化的可能较大,sql变动需要改变java代码
	 * 3.使用PreparedStatement向有占位符号传参数存在硬编码，因为sql语句的where条件不一定，可能多也可能少
	 * 修改sql还需要修改代码，系统不易维护
	 * 4.对结果集解析存在硬编码(查询列名)，sql变化导致解析代码变化，系统不易维护，如果能将数据库记录封装成pojo对象解析比较方便
	 */
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
