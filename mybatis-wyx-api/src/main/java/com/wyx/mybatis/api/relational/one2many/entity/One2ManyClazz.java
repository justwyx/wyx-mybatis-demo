package com.wyx.mybatis.api.relational.one2many.entity;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */
public class One2ManyClazz {
	private Integer id;

	private String clazzNo;

	private String name;

	private List<One2ManyStudent> studentList;

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

	public List<One2ManyStudent> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<One2ManyStudent> studentList) {
		this.studentList = studentList;
	}

	@Override
	public String toString() {
		return "One2ManyClazz{" +
				"id=" + id +
				", clazzNo='" + clazzNo + '\'' +
				", name='" + name + '\'' +
				", studentList=" + studentList +
				'}';
	}
}
