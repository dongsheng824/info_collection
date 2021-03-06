package com.guods.contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guods.toutiao.model.DataUnit;
import com.guods.toutiao.model.ResponseObj;

/**
 * 58同城、头条、企查查解析器
 * @author guods
 *
 */
public class PageParser {

	/**
	 * 解析列表页面，根据页面的标签结构提取联系人信息
	 * VIP会员在列表中没有展示联系人信息，需要获取VIP会员的公司链接，抓取公司页面，在公司页面获取联系人信息
	 * @param document 获取的http页面
	 * @param excel 提取客户信息存到excel
	 */
	public List<String> parse58PageList(Document document, Excel excel, String url){
		List<String> companyUrlList = new ArrayList<String>();
		if (document == null) {
			return null;
		}
		Elements tbodys = document.getElementsByTag("tbody");
		if (tbodys == null || tbodys.size() == 0) {
			return null;
		}
		for (Element tbody : tbodys) {
			Elements trs = tbody.getElementsByTag("tr");
			for (int i = 0; i < trs.size(); i++) {
				//解析列表页面，获取列表信息，并存到excel文档
				Elements tdivs = trs.get(i).getElementsByClass("tdiv");
				Elements sellers = trs.get(i).getElementsByClass("seller");
				if (sellers == null || sellers.size() < 1) {
					continue;
				}
				Element seller = sellers.get(0);
				if (tdivs.size() == 0) {
					//在seller元素里获取公司网址，如果有公司网址则保存公司网址
					Elements sellerAs = seller.getElementsByTag("a");
					if (sellerAs != null && sellerAs.size() > 0) {
						Element company = sellerAs.get(0);
						companyUrlList.add(company.attr("href"));
					}
					continue;
				}
				Element tdiv = trs.get(i).getElementsByClass("tdiv").get(0);
				String desc = tdiv.text();
				Element a = tdiv.getElementsByTag("a").get(0);
				String title = a.text();
				String sellerName = seller.text();
				Element yuyueVertop = trs.get(i).getElementsByClass("yuyue_vertop").get(0);
				String contact = yuyueVertop.attr("data-j4fe");
				//excel插入记录，联系电话没有的数据不记录
				if (contact != null && contact.trim() != "") {
					String[] rowData = {title, desc, sellerName, contact, url};
					excel.insertRow(rowData);
				}
				//在seller元素里获取公司网址，如果有公司网址则保存公司网址
				Elements sellerAs = seller.getElementsByTag("a");
				if (sellerAs != null && sellerAs.size() > 0) {
					Element company = sellerAs.get(0);
					companyUrlList.add(company.attr("href"));
				}
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
	public int parse58PageDet(Document document, Excel excel, String url){
		if (document == null) {
			return 1;
		}
		Elements modBoxs = document.getElementsByClass("mod-box");
		if (modBoxs == null || modBoxs.size() == 0) {
			return 0;
		}
		String[] companyRowData = new String[6];
		//联系人信息存放在modBox中的，解析每个modBox
		for (Element modBox : modBoxs) {
			Elements lis = modBox.getElementsByTag("li");
			//取出每个li标签的字段信息
			for (int i = 0; i < lis.size(); i++) {
				if (lis.get(i).text().contains("[建材]")) {
					Elements as = lis.get(i).getElementsByTag("a");
					if (as.size() >= 2) {
						companyRowData[4] = as.get(1).text() + ";" + companyRowData[4];
						continue;
					}
				}
				if (lis.get(i).text().contains("商家名称：")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						companyRowData[0] = spans.get(0).text();
						continue;
					}
				}
				if (lis.get(i).text().contains("人：") || lis.get(i).text().contains("联&nbsp;&nbsp;系&nbsp;&nbsp;人")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						companyRowData[1] = spans.get(0).text();
						continue;
					}
				}
				if (lis.get(i).text().contains("联系电话：")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						companyRowData[2] = spans.get(0).text();
						continue;
					}
				}
				if (lis.get(i).text().contains("联系地址：")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						companyRowData[3] = spans.get(0).text();
						continue;
					}
				}
			}
		}
		//添加url
		companyRowData[5] = url;
		//保存有效数据
		if (companyRowData[0] != null) {
			excel.insertRow(companyRowData);
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
					String[] rowData = {dataUnit.getTitle(), dataUnit.getSource_url(),
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

	/**
	 * 企查查列表解析
	 * @param document
	 * @param excel
	 * @return
	 */
	public boolean parseQichacha(Document document, Excel excel){
		try {
			if (document == null) {
				return false;
			}
			Elements srchLists = document.getElementsByClass("m_srchList");
			if (srchLists == null || srchLists.size() == 0) {
				return false;
			}
			Elements trs = srchLists.get(0).getElementsByTag("tr");
			for (Element tr : trs) {
				Elements tds = tr.getElementsByTag("td");
				try {
					String[] rowData = new String[2];
					rowData[0] = tds.get(1).text();
					Elements as = tds.get(1).getElementsByTag("a");
					String url = as.get(0).attr("href");
					rowData[1] = url;
					excel.insertRow(rowData);
				} catch (IndexOutOfBoundsException e) {
					continue;
				}
			}
			return true;
		} catch (Exception e) {
			excel.saveFile();
			e.printStackTrace();
			return true;
		}
	}
	/**
	 * 企查查详情页面解析
	 * @param document
	 * @param excel
	 * @param rownum
	 */
	public void parseQichachaDet(Document document, Excel excel, int rownum){
		Elements smalls = document.getElementsByTag("small");
		if (smalls == null || smalls.size() < 2) {
			return;
		}
		excel.writeUnit(rownum, 1, smalls.get(1).text());
		System.out.println(smalls.get(1).text());
		Elements mcls = document.getElementsByClass("m_changeList");
		if (mcls == null || mcls.size() == 0) {
			return;
		}
		Elements trs = mcls.get(0).getElementsByTag("tr");
		for (Element tr : trs) {
			Elements tds = tr.getElementsByTag("td");
			if (tds.get(0).text().contains("经营范围")) {
				excel.writeUnit(rownum, 2, tds.get(1).text());
				System.out.println(tds.get(1).text());
			}
		}
	}
	
}
