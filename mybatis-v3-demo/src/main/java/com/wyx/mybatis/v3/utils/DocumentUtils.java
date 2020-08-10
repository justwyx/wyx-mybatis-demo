package com.wyx.mybatis.v3.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/9
 * @Date : 2020/8/9
 */
public class DocumentUtils {
	public static Document getDocument(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(inputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
}
