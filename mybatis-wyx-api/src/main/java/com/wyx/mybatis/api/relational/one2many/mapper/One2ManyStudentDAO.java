package com.wyx.mybatis.api.relational.one2many.mapper;

import com.wyx.mybatis.api.relational.one2many.entity.One2ManyStudent;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public interface One2ManyStudentDAO {
	One2ManyStudent getClassById(Integer id);

	List<One2ManyStudent> listByClazzNo(String clazzNo);
}
