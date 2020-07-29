package com.wyx.mybatis.api.relational.one2one;

import com.wyx.mybatis.api.relational.one2one.entity.One2OnePerson;
import com.wyx.mybatis.api.relational.one2one.mapper.One2OnePersonDAO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public class App {

	public static void main(String[] args) throws Exception {
		// 读取mybatis-config.xml文件
		InputStream inputStream = Resources.getResourceAsStream("relational/one2one/mybatis-config.xml");

		// 初始化mybatis，创建SqlSessionFactory类的实例
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		// 创建Session实例
		SqlSession session = sqlSessionFactory.openSession();

		// 获得mapper接口的代理对象
		One2OnePersonDAO pm = session.getMapper(One2OnePersonDAO.class);
		// 获取Person对象
		One2OnePerson p2 = pm.getByIdCard("444400111122220001");
		// 打印Person对象
		System.out.println(p2.toString());

		// 提交事务
		session.commit();
		// 关闭Session
		session.close();
	}
}
