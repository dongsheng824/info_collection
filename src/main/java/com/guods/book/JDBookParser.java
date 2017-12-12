package com.guods.book;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.guods.contact.Parser;

public class JDBookParser implements Parser {

	String href;
	String bookName;
	public List<String[]> parseList(Document document) {
		List<String[]> bookItemList = new ArrayList<String[]>();
		Elements glItems = document.getElementsByClass("gl-item");
		for (Element glItem : glItems) {
			Elements imgs = glItem.getElementsByClass("p-name");
			Elements as = imgs.get(0).getElementsByTag("a");
			//url
			href = as.get(0).attr("href");
			//书名
			Elements ems = as.get(0).getElementsByTag("em");
			bookName = ems.get(0).text();
			String[] bookItem = new String[2];
			bookItem[0] = href;
			bookItem[1] = bookName;
			bookItemList.add(bookItem);
		}
		return bookItemList;
	}

	public String[] parseDet(Document document, String[] rowData) {
		Element mc = document.getElementById("product-detail-1");
		Element ul = mc.getElementById("parameter2");
		Elements lis = ul.getElementsByTag("li");
		//详细信息
		for (Element li : lis) {
			System.out.println(li.text());
		}
		System.out.println();
		return null;
	}


}
