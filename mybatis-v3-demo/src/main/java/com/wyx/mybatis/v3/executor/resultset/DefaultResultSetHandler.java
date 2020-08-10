package com.wyx.mybatis.v3.executor.resultset;

import com.wyx.mybatis.v3.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class DefaultResultSetHandler implements ResultSetHandler{
	@Override
	public List<Object> handleResultSet(PreparedStatement preparedStatement, ResultSet resultSet, MappedStatement mappedStatement) throws Exception {
		Class clazz = mappedStatement.getResultTypeClass();

		List<Object> resultList = new ArrayList<>();
		Object result;
		while (resultSet.next()) {
			result = clazz.newInstance();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				String columnName = metaData.getColumnName(i + 1);

				// 通过反射给指点列对应的属性名称赋值
				// 列名和属性名要一致
				Field field = clazz.getDeclaredField(columnName);
				// 暴力破解破坏封装
				field.setAccessible(true);
				field.set(result, resultSet.getObject(columnName));
			}
			resultList.add(result);
		}
		return resultList;
	}
}
