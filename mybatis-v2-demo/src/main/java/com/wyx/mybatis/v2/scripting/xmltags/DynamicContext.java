package com.wyx.mybatis.v2.scripting.xmltags;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @Description :
 * 		sqlNode的动态上下文对象
 *
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class DynamicContext {
	/**
	 * 入参对象 key
	 */
	public static final String PARAMETER_OBJECT_KEY = "_parameter";

	/**
	 * sqlText,sqlNode的单节点SQL全部拼接至此
	 */
	private final StringJoiner sqlBuilder = new StringJoiner(" ");

	private final Map<String, Object> bindings = new HashMap<>();

	public DynamicContext(Object parameterObject) {
		// 为了处理${}时，需要用到入参对象
		this.bindings.put(PARAMETER_OBJECT_KEY, parameterObject);
	}

	public String getSql() {
		return sqlBuilder.toString();
	}
	/**
	 * 拼接sql
	 */
	public void appendSql(String sql) {
		sqlBuilder.add(sql);
	}

	public Map<String, Object> getBindings() {
		return bindings;
	}
}
