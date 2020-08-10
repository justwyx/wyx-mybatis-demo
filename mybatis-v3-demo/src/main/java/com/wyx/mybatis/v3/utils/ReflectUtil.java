package com.wyx.mybatis.v3.utils;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class ReflectUtil {
	public static Class<?> resolveType(String resultType) {
		try {
			Class<?> clazz = Class.forName(resultType);
			return clazz;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
