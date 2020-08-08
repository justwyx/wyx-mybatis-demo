package com.wyx.mybatis.v3.mapping;

/**
 * @author : Just wyx
 * @Description : 封装了#{}，解析出来的参数名称和参数类型
 * @Date : 2020/8/8
 */
public class ParameterMapping {
	private String property;

	private Class<?> javaType = Object.class;

	public static class Builder {
		private ParameterMapping parameterMapping = new ParameterMapping();

		public Builder(String property) {
			parameterMapping.property = property;
		}

		public ParameterMapping build() {
//			resolveTypeHandler();
//			validate();
			return parameterMapping;
		}
	}


	public String getProperty() {
		return property;
	}

	public Class<?> getJavaType() {
		return javaType;
	}
}
