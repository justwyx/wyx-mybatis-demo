package com.wyx.springbootmybatis.mapper.read;


import com.wyx.springbootmybatis.entity.User;

import java.util.List;

public interface UserReader {
    User selectByPrimaryKey(Integer id);

    List<User> list();
}