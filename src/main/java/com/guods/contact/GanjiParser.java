package com.guods.contact;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GanjiParser implements Parser {

	public Object parseList(Document document) {
		List<String[]> list = new ArrayList<String[]>();
		if (document == null) {
			return list;
		}
		//有图的直接提取数据
		Elements listImgs = document.getElementsByClass("list-img");
		if (listImgs != null) {
			for (Element img : listImgs) {
				String[] rowData = new String[6];
				Elements f14s = img.getElementsByClass("f14");
				rowData[0] = f14s.get(0).text();// 标题
				Elements websites = img.getElementsByClass("website");
				rowData[1] = websites.get(0).text();// 名称
				Elements JTelPhoneSpans = img.getElementsByClass("J_tel_phone_span");
				rowData[2] = JTelPhoneSpans.get(0).text();// 电话
				rowData[5] = ganjiUrl(f14s.get(0).attr("href"));
				list.add(rowData);
			}
		}
		//没图的返回地址，进入网站提取数据
		Elements listNoImgs = document.getElementsByClass("list-noimg");
		if (listNoImgs != null) {
			for (Element noImg : listNoImgs) {
				String[] rowData = new String[6];
				Elements f14s = noImg.getElementsByClass("f14");
				rowData[0] = f14s.get(0).text();// 标题
				rowData[5] = ganjiUrl(f14s.get(0).attr("href"));
				list.add(rowData);
			}
		}
		return list;
	}

	public void parseDet(Document document, Excel excel, String[] rowData) {
		//名称
		Elements p1 = document.getElementsByClass("p1");
		if (p1 == null || p1.size() == 0) {
			excel.insertRow(rowData);
			return;
		}
		rowData[1] = p1.text().replace("扫码使用“赶集群组” 微信绑定“赶集叮咚” ", "");
		//联系电话1
		Elements btns = document.getElementsByClass("btn");
		if (btns == null || btns.size() == 0) {
			excel.insertRow(rowData);
			return;
		}
		String gjalog = btns.get(0).attr("gjalog");
		String phone1 = gjalog.substring(gjalog.indexOf("phone=") + 6, gjalog.indexOf("phone=") + 17);
		rowData[2] = phone1;
		//联系电话2
		Elements cons = document.getElementsByClass("con");
		if (cons != null) {
			for (Element con : cons) {
				Elements lis = con.getElementsByTag("li");
				for (Element li : lis) {
					Elements spans = li.getElementsByTag("span");
					if (spans != null && spans.size() > 0 && "联系电话：".equals(spans.get(0).text())) {
						Elements ps = li.getElementsByTag("p");
						rowData[3] = ps.get(0).text();
					}
					if (spans != null && spans.size() > 0 && "联 系 人：".equals(spans.get(0).text())) {
						Elements ps = li.getElementsByTag("p");
						rowData[4] = ps.get(0).text();
					}
				}
			}
		}
		excel.insertRow(rowData);
	}

	/**
	 * 赶集网url转换
	 * @param url
	 * @return
	 */
	private String ganjiUrl(String url){
		if (url.startsWith("http://")) {
			return url;
		}
		if (url.startsWith("//")) {
			return "http:" + url;
		}
		if (url.startsWith("/")) {
			return "http://hz.ganji.com" + url;
		}
		return url;
	}
}
