package com.guods.contact;

import java.util.List;

import org.jsoup.nodes.Document;

public class Main 
{
    public static void main( String[] args )
    {
    	startWork(1, 10);
    }
    
    public static void startWork(int fromPage, int endPage){
    	StringBuffer urlBuffer = new StringBuffer();
    	String preUrl = "http://hz.58.com/zhuangxiu/pn";
    	String postUrl = "/?PGTID=0d300078-0004-f66b-fd74-1a60792d6344&ClickID=1";
    	MyHttpClient myHttpClient = new MyHttpClient();
    	Document document;
    	List<String> companyList;
    	//创建excel文档
    	String[] columnNames = {"标题", "描述", "名字", "联系方式"};
    	Excel excel = new Excel("d:\\contact.xlsx", "contact", columnNames);
    	String[] companyColumnNames = {"公司名", "客户名", "电子邮箱", "联系电话1", "联系电话2", "QQ", "地址"};
    	Excel companyExcel = new Excel("d:\\companyContact.xlsx", "companyContact", companyColumnNames);
    	//创建一个解析器
    	PageParser parser = new PageParser();
    	//修改页码拼接请求，一次循环处理一个页面
    	for (int i = fromPage; i <= endPage; i++) {
    		//清空urlBuffer，重新组装url
    		if (urlBuffer.capacity() > 0) {
    			urlBuffer.delete(0, urlBuffer.capacity());
			}
    		urlBuffer.append(preUrl).append(i).append(postUrl);
    		//访问url，获取document
    		document = myHttpClient.get(urlBuffer.toString());
    		//解析document，并获得公司页面列表
    		companyList = parser.parseListPage(document, excel);
    		System.out.println("第" + i + "页列表采集完成。。。");
    		
    		//如果有公司url，读取公司页面，解析公司页面获取公司页面的联系人信息
    		if (companyList != null && companyList.size() > 0) {
				for (String url : companyList) {
					//获取并解析公司页面
					if (url != null && url.trim() != "") {
						Document companyDocument = myHttpClient.get(url);
						parser.parseCompanyPage(companyDocument, companyExcel);
					}
				}
			}
    		System.out.println("第" + i + "页公司采集完成。。。");
		}
    	//数据存档
    	excel.saveFile();
    	companyExcel.saveFile();
    	System.out.println("采集完成，列表总共:" + excel.size() + "条，公司总共" + companyExcel.size() + "条！");
    }
}
