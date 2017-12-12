package com.guods.exam;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class K12Parser {

	public Map<String, String> parseList(Document document) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Elements resList = document.getElementsByClass("res-list-item");
		for (Element res : resList) {
			Elements covers = res.getElementsByClass("cover");
			Elements as = covers.get(0).getElementsByTag("a");
			String url = as.get(0).attr("href");
			String title = as.get(0).attr("title");
			resultMap.put("http://rc.k12.com.cn" + url, title);
		}
		return resultMap;
	}

	public Map<String, String> parseDet(Document document) {
		//获取url
		Map<String, String> result = new HashMap<String, String>();
		try {
			Elements resList = document.getElementsByClass("res-show-download");
			Elements as = resList.get(0).getElementsByTag("script");
			String url = as.get(0).attr("src");
			url = url.substring(url.indexOf("/id"));
//		System.out.println(url);
			//获取文件名
			Elements boxs = document.getElementsByClass("box");
			Elements titles = boxs.get(0).getElementsByClass("title");
			String fileName = titles.get(1).text();
			result.put("http://rc.k12.com.cn/cloudRes/res/download" + url, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
}
