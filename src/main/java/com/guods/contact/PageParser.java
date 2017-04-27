package com.guods.contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guods.toutiao.model.DataUnit;
import com.guods.toutiao.model.ResponseObj;

public class PageParser {

	/**
	 * 解析列表页面，根据页面的标签结构提取联系人信息
	 * VIP会员在列表中没有展示联系人信息，需要获取VIP会员的公司链接，抓取公司页面，在公司页面获取联系人信息
	 * @param document 获取的http页面
	 * @param excel 提取客户信息存到excel
	 */
	public List<String> parseListPage(Document document, Excel excel){
		List<String> companyUrlList = new ArrayList<String>();
		if (document == null) {
			return null;
		}
		Elements tbodys = document.getElementsByTag("tbody");
		if (tbodys == null || tbodys.size() == 0) {
			return null;
		}
		Element tbody = tbodys.get(0);
		Elements trs = tbody.getElementsByTag("tr");
		for (int i = 0; i < trs.size(); i++) {
			//解析列表页面，获取列表信息，并存到excel文档
			Elements tdivs = trs.get(i).getElementsByClass("tdiv");
			if (tdivs.size() == 0) {
				continue;
			}
			Element tdiv = trs.get(i).getElementsByClass("tdiv").get(0);
			String desc = tdiv.text();
			Element a = tdiv.getElementsByTag("a").get(0);
			String title = a.text();
			Element seller = tdiv.getElementsByClass("seller").get(0);
			String sellerName = seller.text();
			Element yuyueVertop = trs.get(i).getElementsByClass("yuyue_vertop").get(0);
			String contact = yuyueVertop.attr("data-j4fe");
			//excel插入记录，联系电话没有的数据不记录
			if (contact != null && contact.trim() != "") {
				String[] rowData = {title, desc, sellerName, contact};
				excel.insertRow(rowData);
			}
			//在seller元素里获取公司网址，如果有公司网址则保存公司网址
			Elements sellerAs = seller.getElementsByTag("a");
			if (sellerAs != null && sellerAs.size() > 0) {
				Element company = sellerAs.get(0);
				companyUrlList.add(company.attr("href"));
			}
		}
		//返回公司网址，以便后续根据公司网址抓取页面，从公司页面获取联系人信息
		return companyUrlList;
	}
	
	/**
	 * 
	 * 解析公司页面，在公司页面获取联系方式
	 * @param document
	 * @param excel
	 */
	public int parseCompanyPage(Document document, Excel excel){
		if (document == null) {
			return 1;
		}
		Elements w_990 = document.getElementsByClass("w_990");
		if (w_990 != null && w_990.size() > 0) {
			return 2;
		}
		Elements modBoxs = document.getElementsByClass("mod-box");
		if (modBoxs == null || modBoxs.size() == 0) {
			return 0;
		}
		String[] companyRowData = new String[7];
		//联系人信息存放在modBox中的，解析每个modBox
		for (Element modBox : modBoxs) {
			Elements lis = modBox.getElementsByTag("li");
			if (lis == null || lis.size() == 0) {
				return 1;
			}
			//取出每个li标签的字段信息
			int min = lis.size() > companyRowData.length ? companyRowData.length : lis.size();
			for (int i = 0; i < min; i++) {
				Elements spans = lis.get(i).getElementsByTag("span");
				if (spans == null || spans.size() == 0) {
					continue;
				}
				Element span = spans.get(0);
				companyRowData[i] = span.text();
			}
			//保存有效数据
			if (companyRowData[1] != null && companyRowData[6] != null) {
				excel.insertRow(companyRowData);
			}
		}
		return 1;
	}
	
	/**
	 * 今日头条
	 * @param document
	 * @param excel
	 * @param toutiaoCommCount
	 * @param search
	 * @return
	 */
	public boolean parseToutiao(Document document, Excel excel, int toutiaoCommCount, boolean search, String keyword){
		boolean tag = false;
		if (document == null) {
			return tag;
		}
		String text = document.body().text();
		text = text.replace("abstract", "abstract0");
		System.out.println(text);
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseObj result = null;
		try {
			//解析json串
			result = objectMapper.readValue(text, ResponseObj.class);
			//获取数据，存excel
			List<DataUnit> dataList = result.getData();
			for (DataUnit dataUnit : dataList) {
				Integer commentCount = dataUnit.getComments_count();
				if (commentCount != null && commentCount >= toutiaoCommCount
						&& (dataUnit.getTitle().contains(keyword) || keyword == null || keyword.trim() == "")) {
					String[] rowData = {dataUnit.getTitle(), "http://www.toutiao.com/" + dataUnit.getSource_url(),
							commentCount.toString(), ""};
					if (!excel.contains(rowData[1])) {
						excel.insertRow(rowData);
						tag = true;
					}
				}
			}
			//搜索单独处理
			if (search ) {
				if (result.getHas_more() != null && result.getHas_more() ) {
					tag = true;
				}else {
					tag = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tag;
	}
	
	public Map<String, String[]> parseGanjiList(Document document, Excel excel){
		Map<String, String[]> noImgMap = new HashMap<String, String[]>();
		//有图的直接提取数据
		Elements listImgs = document.getElementsByClass("list-img");
		for (Element img : listImgs) {
			String[] rowData = new String[3];
			Elements f14s = img.getElementsByClass("f14");
			rowData[0] = f14s.get(0).text();// 标题
			Elements websites = img.getElementsByClass("website");
			rowData[1] = websites.get(0).text();// 名称
			Elements JTelPhoneSpans = img.getElementsByClass("J_tel_phone_span");
			rowData[2] = JTelPhoneSpans.get(0).text();// 电话
			excel.insertRow(rowData);
		}
		//没图的返回地址，进入网站提取数据
		Elements listNoImgs = document.getElementsByClass("list-noimg");
		for (Element noImg : listNoImgs) {
			String[] rowData = new String[4];
			Elements f14s = noImg.getElementsByClass("f14");
			rowData[0] = f14s.get(0).text();// 标题
			noImgMap.put("http://hz.ganji.com" + f14s.get(0).attr("href"), rowData);
		}
		return noImgMap;
	}
	
	public void parseGanjiSecond(Document document, Excel excel, String[] rowData){
		//名称
		Elements p1 = document.getElementsByClass("p1");
		rowData[1] = p1.text();
		//联系电话1
		Elements btns = document.getElementsByClass("btn");
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
						break;
					}
				}
			}
		}
		excel.insertRow(rowData);
	}
}
