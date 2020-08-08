package com.wyx.mybatis.v3.scripting.xmltags;

/**
 * @Description : sql节点接口
 * @author : Just wyx
 * @Date : 2020/8/8
 */
public interface SqlNode {

	boolean apply(DynamicContext context);
}
