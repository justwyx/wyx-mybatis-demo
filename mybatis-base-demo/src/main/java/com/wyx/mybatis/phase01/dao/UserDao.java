package com.wyx.mybatis.phase01.dao;


import com.wyx.mybatis.phase01.po.User;

public interface UserDao {

	User findUserById(int id);
}
