package com.wyx.jdbc.sqlnode;

import com.wyx.jdbc.framework.config.DynamicContext;

import java.util.List;

/**
 * @Description : 封装了一组sqlNode
 * @author : Just wyx
 * @Date : 2020/8/4
 */
public class MixedSqlNode implements SqlNode{

	private List<SqlNode> sqlNodeList;

	public MixedSqlNode(List<SqlNode> sqlNodeList) {
		this.sqlNodeList = sqlNodeList;
	}

	@Override
	public void apply(DynamicContext context) {
		for(SqlNode sqlNode : sqlNodeList){
			sqlNode.apply(context);
		}
	}
}
