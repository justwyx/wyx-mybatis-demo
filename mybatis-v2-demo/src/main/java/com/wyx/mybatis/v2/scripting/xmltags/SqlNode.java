package com.wyx.mybatis.v2.scripting.xmltags;

/**
 * @Description :
 * 个人理解:
 * 		SqlNode为单个sql节点对象，相与之间相与关联，最终为一个树型结构
 * 		SqlNode可为分各种类型
 * 			例：
 * 		SqlNode对其进行抽象化，最终各种sqlNode对象都只需要关心apply方法
 * 	 	将sql进行串连起来
 * @author : Just wyx
 * @Date : 2020/8/5
 */
public interface SqlNode {
	boolean apply(DynamicContext context);
}
