package com.wyx.mybatis.v3.io;

import java.io.InputStream;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class Resources {

	public static InputStream getResourceAsStream(String location) {
		return Resources.class.getClassLoader().getResourceAsStream(location);
	}
}
