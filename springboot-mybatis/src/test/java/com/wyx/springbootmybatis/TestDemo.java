package com.wyx.springbootmybatis;

import com.wyx.springbootmybatis.entity.User;
import com.wyx.springbootmybatis.mapper.read.UserReader;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	}

}
