package com.wyx.mybatis.v3.executor.parameter;

import com.wyx.mybatis.v3.executor.parameter.ParameterHandler;
import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.ParameterMapping;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class DefaultParameterHandler implements ParameterHandler {
	@Override
	public void setParameters(Object param, PreparedStatement preparedStatement, BoundSql boundSql) throws SQLException {
		if (param instanceof Integer || param instanceof String) {
			// 设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
			preparedStatement.setObject(1, param);
		} else if (param instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) param;

			// 解析#{}之后封装的参数集合List<ParameterMapping>
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				String name = parameterMapping.getProperty();
				Object value = map.get(name);
				// 给map集合中的参数赋值
				preparedStatement.setObject(i + 1, value);
			}

		} else {
			//
		}
	}
}
