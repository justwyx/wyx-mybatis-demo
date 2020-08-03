package com.wyx.springbootmybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyx.springbootmybatis.entity.User;
import com.wyx.springbootmybatis.mapper.read.UserReader;
import com.wyx.springbootmybatis.pagehelper.PageBean;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/30
 * @Date : 2020/7/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

	@Autowired
	private UserReader userReader;

	@Test
	public void get() {
		User user = Optional.ofNullable(userReader.selectByPrimaryKey(1)).orElse(new User());
		System.out.println(user.toString());

		List<User> list = userReader.list();
		System.out.println(list);

	}

	@Test
	public void pageHelperTest() {
		// 开启分页插件,放在查询语句上面 帮助生成分页语句
		//底层实现原理采用改写语句   将下面的方法中的sql语句获取到然后做个拼接 limit  AOPjishu
		PageHelper.startPage(1, 2);
		List<User> listDemo = userReader.list();


		List<User> listDemo1 = userReader.list();


		// 封装分页之后的数据  返回给客户端展示  PageInfo做了一些封装 作为一个类
		PageBean<User> pageInfoDemo = new PageBean<User>(listDemo1);
		System.out.println(pageInfoDemo.toString());
	}

}
