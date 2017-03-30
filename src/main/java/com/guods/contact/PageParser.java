package com.guods.contact;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
}