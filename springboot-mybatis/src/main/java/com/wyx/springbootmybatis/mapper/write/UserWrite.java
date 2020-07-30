package com.wyx.springbootmybatis.mapper.write;

import com.wyx.springbootmybatis.entity.User;

public interface UserWrite {
    int insert(User record);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}