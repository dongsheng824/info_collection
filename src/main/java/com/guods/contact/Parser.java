package com.guods.contact;

import org.jsoup.nodes.Document;

/**
 * 两步解析：
 * 1、获取列表，解析列表的内容和详情url
 * 2、解析详情页
 * @author guods
 *
 */
public interface Parser {

	Object parseList(Document document);
	void parseDet(Document document, Excel excel, String[] rowData);
	
}
