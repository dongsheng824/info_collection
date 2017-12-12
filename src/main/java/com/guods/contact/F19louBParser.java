package com.guods.contact;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class F19louBParser implements Parser {

	public List<String[]> parseList(Document document) {
		List<String[]> list = new ArrayList<String[]>();
		Elements lists = document.getElementsByClass("list_0");
		Elements lis = lists.get(0).getElementsByTag("li");
		for (Element li : lis) {
			String[] rowData = new String[3];
			Element a = li.getElementsByTag("a").get(0);
			rowData[0] = a.attr("href");
			rowData[1] = a.attr("title");
			list.add(rowData);
		}
		return list;
	}

	public String[] parseDet(Document document, String[] rowData) {
		if (document == null) {
			return rowData;
		}
		Elements conts = document.getElementsByClass("post-cont");
		if (conts.size() == 0) {
			rowData[2] = "该帖子被屏蔽:(";
		}else {
			rowData[2] = conts.get(0).getElementsByTag("div").get(0).text();
		}
		return rowData;
	}

}
