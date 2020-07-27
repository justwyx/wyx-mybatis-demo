package com.wyx.mybatis.phase02.mapper;


import com.wyx.mybatis.phase02.po.User;

public interface UserMapper {
	User findUserById(int id);
}
