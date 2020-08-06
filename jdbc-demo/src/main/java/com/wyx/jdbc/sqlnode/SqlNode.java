package com.wyx.jdbc.sqlnode;

import com.wyx.jdbc.framework.config.DynamicContext;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/4
 * @Date : 2020/8/4
 */
public interface SqlNode {
	void apply(DynamicContext context);
}
