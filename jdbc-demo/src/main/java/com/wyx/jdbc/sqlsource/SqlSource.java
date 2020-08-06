package com.wyx.jdbc.sqlsource;

import com.wyx.jdbc.framework.config.BoundSql;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/1
 * @Date : 2020/8/1
 */
public interface SqlSource {
	/**
	 * 针对封装的SQL信息，去进行解析，获取可以直接执行的SQL语句
	 */
	BoundSql getBoundSql(Object param);

}
