package com.guods.contact;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.guods.model.Entity58;

public class T58Parser{

	public List<Entity58> parseList(Document document) {
		List<Entity58> rowDataList = new ArrayList<Entity58>();
		if (document == null) {
			return new ArrayList<Entity58>();
		}
		Elements tables = document.getElementsByClass("small-tbimg");
		for (Element table : tables) {
			Elements trs = table.getElementsByTag("tr");
			System.out.println(trs.size());
			for (int i = 0; i < trs.size(); i++) {
				Entity58 entity58 = new Entity58();
				//解析列表页面，获取列表信息，并存到excel文档
				Elements tdivs = trs.get(i).getElementsByClass("tdiv");
				if (tdivs != null && tdivs.size() > 0) {
					Element tdiv = trs.get(i).getElementsByClass("tdiv").get(0);
					//内容
					entity58.setArticleContent(tdiv.text());
					Element a = tdiv.getElementsByTag("a").get(0);
					//帖子标题
					entity58.setArticleTitle(a.text());
					//帖子url
					entity58.setArticleUrl(a.attr("href"));
					
					Elements sellers = trs.get(i).getElementsByClass("seller");
					if (sellers != null || sellers.size() > 0) {
						Element seller = sellers.get(0);
						String sellerName = seller.text();
						//公司名或个人名
						entity58.setUserName(sellerName);
						//在seller元素里获取公司网址，如果有公司网址则保存公司网址
						Elements sellerAs = seller.getElementsByTag("a");
						if (sellerAs != null && sellerAs.size() > 0) {
							//公司url
							entity58.setCompanyUrl(sellerAs.get(0).attr("href"));
						}
					}
				}
				Elements yuyueVertops = trs.get(i).getElementsByClass("yuyue_vertop");
				if (yuyueVertops != null && yuyueVertops.size() > 0) {
					Element yuyueVertop = yuyueVertops.get(0);
					String contact = yuyueVertop.attr("data-j4fe");
					//联系方式
					entity58.setArticleContact(contact);
				}
				rowDataList.add(entity58);
			}
		}
		//返回公司网址，以便后续根据公司网址抓取页面，从公司页面获取联系人信息
		return rowDataList;
	}

	public Entity58 parseDet(Document document, Entity58 entity58) {
		if (document == null) {
			return entity58;
		}
		Elements modBoxs = document.getElementsByClass("mod-box");
		if (modBoxs == null || modBoxs.size() == 0) {
			return entity58;
		}
		//联系人信息存放在modBox中的，解析每个modBox
		for (Element modBox : modBoxs) {
			Elements lis = modBox.getElementsByTag("li");
			//取出每个li标签的字段信息
			for (int i = 0; i < lis.size(); i++) {
//				if (lis.get(i).text().contains("[建材]")) {
//					Elements as = lis.get(i).getElementsByTag("a");
//					if (as.size() >= 2) {
//						rowData[7] = as.get(1).text() + ";" + rowData[7];
//						continue;
//					}
//				}
				if (lis.get(i).text().contains("商家名称：")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						entity58.setCompanyName(spans.get(0).text());
						continue;
					}
				}
				if (lis.get(i).text().contains("人：") || lis.get(i).text().contains("联&nbsp;&nbsp;系&nbsp;&nbsp;人")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						entity58.setCompanyContactName(spans.get(0).text());
						continue;
					}
				}
				if (lis.get(i).text().contains("联系电话：")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						entity58.setCompanyPhone(entity58.getCompanyPhone() + "," + spans.get(0).text());
						continue;
					}
				}
				if (lis.get(i).text().contains("联系地址：")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						entity58.setCompanyAddress(spans.get(0).text());
						continue;
					}
				}
				if (lis.get(i).text().contains("电子邮箱：")) {
					Elements spans = lis.get(i).getElementsByTag("span");
					if (spans != null && spans.get(0).text() != null) {
						entity58.setEmail(spans.get(0).text());
						continue;
					}
				}
			}
		}
		Elements contents = document.getElementsByClass("aboutus-content");
		//企业介绍
		if (contents.size() > 0) {
			entity58.setCompanyIntro(contents.get(0).text());
		}
		return entity58;
	}

	public Entity58 parseDet2(Document document, Entity58 entity58) {
		Element mtit = document.getElementsByClass("mtit_con_left").get(0);
		Element time = mtit.getElementsByClass("time").get(0);
		//发布时间
		entity58.setArticleCreateTime(time.text());
		Element count = mtit.getElementsByClass("count").get(0);
		entity58.setArticleReadCount(count.text());
		//属性
		Element attrs = document.getElementsByClass("suUl").get(0);
		Elements lis = attrs.getElementsByTag("li");
		for (Element li : lis) {
			Elements divs = li.getElementsByTag("div");
			if (divs.get(0).text().contains("类       别：")) {
				entity58.setArticleType(divs.get(1).text());
				continue;
			}
			if (divs.get(0).text().contains("服务区域：")) {
				entity58.setArticleSeviceRegion(divs.get(1).text());
				continue;
			}
			if (divs.get(0).text().contains("商家地址：")) {
				entity58.setArticleAddr(divs.get(1).text());
				continue;
			}
		}
		//详情描述
		Element article = document.getElementsByTag("article").get(0);
		entity58.setArticleDesc(article.text());
		return entity58;
	}
}
