package com.wyx.mybatis.v3;

import com.wyx.mybatis.v3.entity.User;
import com.wyx.mybatis.v3.io.Resources;
import com.wyx.mybatis.v3.session.SqlSession;
import com.wyx.mybatis.v3.session.SqlSessionFactory;
import com.wyx.mybatis.v3.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/8
 * @Date : 2020/8/8
 */
@SuppressWarnings("all")
public class TestV3 {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void before() {
		// 全局配置文件的路径
		String location = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(location);

		// 创建SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testV3() {
		// 执行查询
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("sex", 2);
		paramMap.put("username", "王五");

		SqlSession sqlSession = sqlSessionFactory.openSession();

		List<User> userList = sqlSession.selectList("test.queryUserByParams", paramMap);
		System.out.println(userList);
	}
}
