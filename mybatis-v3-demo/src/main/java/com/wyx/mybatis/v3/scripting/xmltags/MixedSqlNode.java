package com.wyx.mybatis.v3.scripting.xmltags;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : 混合sqlNode
 * @Date : 2020/8/8
 */
public class MixedSqlNode implements SqlNode{
	private final List<SqlNode> contents;

	public MixedSqlNode(List<SqlNode> contents) {
		this.contents = contents;
	}

	@Override
	public boolean apply(DynamicContext context) {
		/**
		 * 混合sqlNode自己不包含sql信息，只关联sqlNode集合
		 */
		contents.forEach(node -> node.apply(context));
		return true;
	}
}
