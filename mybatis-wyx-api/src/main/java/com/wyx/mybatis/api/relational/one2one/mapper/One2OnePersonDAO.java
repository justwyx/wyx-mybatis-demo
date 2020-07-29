package com.wyx.mybatis.api.relational.one2one.mapper;

import com.wyx.mybatis.api.relational.one2one.entity.One2OnePerson;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public interface One2OnePersonDAO {
	One2OnePerson getByIdCard(String idCard);

}
