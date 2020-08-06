package com.wyx.mybatis.v2.scripting.xmltags;

import java.util.List;

/**
 * @Description : 混合sqlNode
 * 记录的是一组sqlNode
 *
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public class MixedSqlNode implements SqlNode{
	private final List<SqlNode> contents;

	public MixedSqlNode(List<SqlNode> contents) {
		this.contents = contents;
	}

	@Override
	public boolean apply(DynamicContext context) {
		// 因为自己并不记录sql，只需将有关联的sqlNode进行apply
		contents.forEach(node -> node.apply(context));
		return true;
	}
}
