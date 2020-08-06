package com.wyx.jdbc.framework.config;

/**
 * @author : Just wyx
 * @Description : 封装了#{}，解析出来的参数名称和参数类型
 * @Date : 2020/8/2
 */
public class ParameterMapping {
	private String name;

	private Class type;

	public ParameterMapping(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
