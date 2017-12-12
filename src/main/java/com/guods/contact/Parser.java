package com.guods.contact;

import java.util.List;

import org.jsoup.nodes.Document;

/**
 * 两步解析：
 * 1、获取列表，解析列表的内容和详情url
 * 2、解析详情页
 * @author guods
 *
 */
public interface Parser {

	List<String[]> parseList(Document document);
	String[] parseDet(Document document, String[] rowData);
	
}
