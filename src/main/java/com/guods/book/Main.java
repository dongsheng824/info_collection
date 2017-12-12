package com.guods.book;

import java.util.List;

import org.jsoup.nodes.Document;

import com.guods.contact.JsoupHttpClient;
import com.guods.contact.Parser;

public class Main {

	private static Parser parser = new JDBookParser();
	private static JsoupHttpClient myHttpClient = new JsoupHttpClient();
	public static void main(String[] args) {
		List<String[]> bookList = getBookList("https://list.jd.com/list.html?cat=", "1713,3267,3468", 3);
		for (String[] book : bookList) {
			getBookDetail("http:" + book[0]);
			getBookDesc("https://dx.3.cn/desc/" + "10831234");
		}
	}
	//获取页面的图书列表
	public static List<String[]> getBookList(String url, String cat, int page){
		StringBuilder builder = new StringBuilder(url);
		builder.append(cat).append("&page=").append(page);
		Document document = myHttpClient.get(builder.toString());
		List<String[]> bookList = parser.parseList(document);
		return bookList;
	}
	//获取图书详情
	public static void getBookDetail(String url){
		Document document = myHttpClient.get(url);
		parser.parseDet(document, null);
	}
	//获取图书描述
	public static void getBookDesc(String url){
		
	}
}
