package com.wyx.mybatis.v3.session;

import com.wyx.mybatis.v3.builder.xml.XMLConfigBuilder;
import com.wyx.mybatis.v3.session.defaults.DefaultSqlSessionFactory;
import com.wyx.mybatis.v3.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class SqlSessionFactoryBuilder {

	/**
	 * 根据字符流生成 SqlSessionFactory
	 */
	public SqlSessionFactory build(InputStream inputStream) {
		// 获取 Configuration对象（通过XMLConfigBuilder解析）
		Document document = DocumentUtils.getDocument(inputStream);
		XMLConfigBuilder parser = new XMLConfigBuilder();

		Configuration configuration = parser.parse(document.getRootElement());
		// 创建 SqlSessionFactory
		return build(configuration);
	}


	/**
	 * 根据字节流生成 SqlSessionFactory
	 */
	public SqlSessionFactory build(Reader reader) {
		return null;
	}


	/**
	 * 最终都是走这里，创建一个默认的SqlSessionFactory
	 * @param config
	 * @return
	 */
	public SqlSessionFactory build(Configuration config) {
		return new DefaultSqlSessionFactory(config);
	}
}
