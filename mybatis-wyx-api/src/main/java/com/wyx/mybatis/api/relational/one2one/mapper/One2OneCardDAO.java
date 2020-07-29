package com.wyx.mybatis.api.relational.one2one.mapper;

import com.wyx.mybatis.api.relational.one2one.entity.One2OneCard;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public interface One2OneCardDAO {

	One2OneCard getByIdCard(String idCard);
}
