package com.wyx.mybatis.v3.executor.statement;

import com.wyx.mybatis.v3.mapping.BoundSql;
import com.wyx.mybatis.v3.mapping.MappedStatement;
import com.wyx.mybatis.v3.mapping.ParameterMapping;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public interface StatementHandler {

	public Statement prepare(String sql, Connection connection) throws SQLException;


	public void setParameters(Object param, Statement statement, BoundSql boundSql) throws SQLException;

	public List<Object> query(Statement statement, MappedStatement mappedStatement, List<Object> resultList) throws Exception;


}
