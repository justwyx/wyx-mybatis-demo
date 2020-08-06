package com.wyx.mybatis.v2.mapping;

/**
 * @Description : 封装了#{}，解析出来的参数名称和参数类型
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class ParameterMapping {
	private String property;
	private Class<?> javaType = Object.class;

	public ParameterMapping(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}
}
