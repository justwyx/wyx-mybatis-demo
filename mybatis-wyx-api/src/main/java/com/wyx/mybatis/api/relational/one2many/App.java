package com.wyx.mybatis.api.relational.one2many;

import com.wyx.mybatis.api.relational.one2many.entity.One2ManyClazz;
import com.wyx.mybatis.api.relational.one2many.entity.One2ManyStudent;
import com.wyx.mybatis.api.relational.one2many.mapper.One2ManyClazzDAO;
import com.wyx.mybatis.api.relational.one2many.mapper.One2ManyStudentDAO;
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
		InputStream inputStream = Resources.getResourceAsStream("relational/one2many/mybatis-config.xml");

		// 初始化mybatis，创建SqlSessionFactory类的实例
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		// 创建Session实例
		SqlSession session = sqlSessionFactory.openSession();

		// 获得mapper接口的代理对象
		One2ManyClazzDAO clazzDAO = session.getMapper(One2ManyClazzDAO.class);
		// 获取班级及关联的学生信息
		One2ManyClazz clazz = clazzDAO.getStudentByClazzNo("2020001");
		// 打印
		System.out.println("One2ManyClazz: " + clazz.toString());

		// 获得mapper接口的代理对象
		One2ManyStudentDAO studentDAO = session.getMapper(One2ManyStudentDAO.class);
		// 获取班级及关联的学生信息
		One2ManyStudent student = studentDAO.getClassById(10);
		// 打印
		System.out.println("One2ManyStudent: " + student.toString());


		// 提交事务
		session.commit();
		// 关闭Session
		session.close();
	}
}
