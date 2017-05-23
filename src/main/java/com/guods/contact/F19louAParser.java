package com.guods.contact;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class F19louAParser implements Parser {

	public Object parseList(Document document) {
		Elements jItems = document.getElementsByClass("J_item");
		Elements h3s;
		List<String[]> list = new ArrayList<String[]>();
		for (Element item : jItems) {
			String[] rowData = new String[3];
			rowData[0] = item.attr("data-url");
			h3s = item.getElementsByTag("h3");
			if (h3s != null && h3s.size() > 0) {
				rowData[1] = h3s.get(0).text();
			}
			list.add(rowData);
		}
		return list;
	}

	public void parseDet(Document document, Excel excel, String[] rowData) {
		Elements conts = document.getElementsByClass("post-cont");
		if (conts.size() == 0) {
			rowData[2] = "该帖子被屏蔽:(";
		}else {
			rowData[2] = conts.get(0).text();
		}
		excel.insertRow(rowData);

	}

}
