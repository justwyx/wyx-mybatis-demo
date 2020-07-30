package com.wyx.springbootmybatis.mapper.read;


import com.wyx.springbootmybatis.entity.User;

public interface UserReader {
    User selectByPrimaryKey(Integer id);
}