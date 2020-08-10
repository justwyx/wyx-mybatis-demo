package com.wyx.mybatis.v3.executor.parameter;

import com.wyx.mybatis.v3.mapping.BoundSql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Just wyx
 * @Description : 用于处理JDBC执行过程中入参对象
 * @Date : 2020/8/9
 */
public interface ParameterHandler {
	void setParameters(Object param, PreparedStatement preparedStatement, BoundSql boundSql) throws SQLException;
}
