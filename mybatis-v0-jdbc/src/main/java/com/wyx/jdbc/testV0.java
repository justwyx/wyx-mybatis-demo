package com.wyx.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * @Description : 通过jdbc连接操作数据库
 * @author : Just wyx
 * @Date : 2020/8/10
 */
public class testV0 {

	/**
	 *JDBC问题:
	 * 1.数据库连接创建,释放频繁造成系统资源浪费,从而影响系统性能
	 * 2.SQL语句在代码中硬编码,造成代码不易维护,实际应用中sql变化的可能较大,sql变动需要改变java代码
	 * 3.使用PreparedStatement向有占位符号传参数存在硬编码,因为sql语句的where条件不一定,可能多也可能少,
	 * 	 修改sql还需要修改代码,系统不易维护
	 * 4.对结果集解析存在硬编码(查询列名),sql变化导致解析代码变化,系统不易维护,如果能将数据库记录封装成pojo对象解析比较方便
	 *
	 *v0优化方案：
	 * 1，对于数据库频繁创建连接，释放资源问题：用连接池代码
	 * 2，对于sql语句及入参硬编码问题：将sql与返回类型移入property文件，解决sql语句要代码中硬编码问题
	 * 3，对获取结果集硬编码问题：通过配置返回结果果类型与反射机制
	 */
	@Test
	public void testJdbc() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			// 加载驱动，jdk6之后也可以不加载
//			Class.forName("com.mysql.cj.jdbc.Driver");
			// 获取连接
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wyx_mybatis_demo?characterEncoding=utf-8", "root", "root");
			// 定义sql语句，？表示占位符
			String sql = "select * from user where username = ?";
			// 使用预处理对象对sql语句进行预处理
			statement = connection.prepareStatement(sql);
			// 对之前sql语句中的占位符进行赋值替换
			statement.setObject(1, "李四");
			// 执行，获取结果集
			resultSet = statement.executeQuery();
			// 输出结果集
			while (resultSet.next()) {
				System.out.println("id:" + resultSet.getObject("id") + ",username:" + resultSet.getObject("username"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//释放资源,逆向释放
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
