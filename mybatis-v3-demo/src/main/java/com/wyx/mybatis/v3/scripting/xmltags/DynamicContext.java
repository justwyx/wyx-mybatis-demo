package com.wyx.mybatis.v3.scripting.xmltags;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author : Just wyx
 * @Description : sqlNode动态上下文对象
 * @Date : 2020/8/8
 */
public class DynamicContext {
	public static final String PARAMETER_OBJECT_KEY = "_parameter";

	/**
	 * sqlNode中的sql全部拼接至此
	 */
	private final StringJoiner sqlBuilder = new StringJoiner(" ");

	private final Map<String, Object> bindings = new HashMap<>();

	public DynamicContext(Object parameterObject) {
		this.bindings.put(PARAMETER_OBJECT_KEY, parameterObject);
	}

	/**
	 * 获取sql
	 */
	public String getSql() {
		return sqlBuilder.toString().trim();
	}

	/**
	 * 供sqlNode apply使用，拼接sql
	 */
	public void appendSql(String sql) {
		sqlBuilder.add(sql);
	}


	public Map<String, Object> getBindings() {
		return bindings;
	}
}
