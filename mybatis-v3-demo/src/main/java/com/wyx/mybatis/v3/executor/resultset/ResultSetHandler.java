package com.wyx.mybatis.v3.executor.resultset;

import com.wyx.mybatis.v3.mapping.MappedStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : 用于处理结果集对象
 * @Date : 2020/8/9
 */
public interface ResultSetHandler {
	List<Object> handleResultSet(PreparedStatement preparedStatement, ResultSet resultSet, MappedStatement mappedStatement) throws Exception;
}

