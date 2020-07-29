package com.wyx.mybatis.api.relational.one2many.entity;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public class One2ManyStudent {
	private Integer id;

	private String clazzNo;

	private String name;

	private Byte sex;

	private One2ManyClazz clazz;



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClazzNo() {
		return clazzNo;
	}

	public void setClazzNo(String clazzNo) {
		this.clazzNo = clazzNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public One2ManyClazz getClazz() {
		return clazz;
	}

	public void setClazz(One2ManyClazz clazz) {
		this.clazz = clazz;
	}


	@Override
	public String toString() {
		return "One2ManyStudent{" +
				"id=" + id +
				", clazzNo='" + clazzNo + '\'' +
				", name='" + name + '\'' +
				", sex=" + sex +
				", clazz=" + clazz +
				'}';
	}
}
