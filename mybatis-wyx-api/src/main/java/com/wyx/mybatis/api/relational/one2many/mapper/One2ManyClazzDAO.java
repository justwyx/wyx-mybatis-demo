package com.wyx.mybatis.api.relational.one2many.mapper;

import com.wyx.mybatis.api.relational.one2many.entity.One2ManyClazz;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public interface One2ManyClazzDAO {

	One2ManyClazz getByClazzNo(String clazzNo);

	One2ManyClazz getStudentByClazzNo(String clazzNo);
}
