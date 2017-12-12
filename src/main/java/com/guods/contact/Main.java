package com.guods.contact;

import java.util.List;

import javax.swing.SwingUtilities;

import org.jsoup.nodes.Document;

import com.guods.model.Entity58;
import com.guods.util.Translate;
import com.guods.view.MainPanel;

public class Main 
{
    public static void main( String[] args )
    {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainPanel();
			}
		});
    	
    	//////////////////////////////////
    	//企查查采集
//    	Qichacha qichacha = new Qichacha();
//    	qichacha.getData();
    	
//    	startWork(1, 1, "http://hz.58.com/jiazhuang/pn", 
//    			"/?PGTID=0d300261-0004-f3d8-c640-bd963a4d7964&ClickID=23", "d://result//", "58result.xlsx");
    }
    
    
    public static void startWork(int fromPage, int endPage, String preUrl, String postUrl, String filePath, String fileName) {
		StringBuffer urlBuffer = new StringBuffer();
		JsoupHttpClient myHttpClient = new JsoupHttpClient();
		Document document;
		Entity58 parseResult = null;
		// 创建excel文档
		String[] columnNames = { "标题", "类别", "文章url", "文章内容", "文章联系地址", "文章联系人", "文章内容", 
				"文章创建时间", "文章阅读次数","服务区域","公司名","公司地址","公司联系人","公司联系电话","公司简介","公司url",
				"公司email","用户名"};
		Excel excel = new Excel(filePath, fileName, "contact", columnNames);
		// 创建一个解析器
		T58Parser parser = new T58Parser();
		// 修改页码拼接请求，一次循环处理一个页面
		for (int i = fromPage; i <= endPage ; i++) {
			// 清空urlBuffer，重新组装url
			if (urlBuffer.capacity() > 0) {
				urlBuffer.delete(0, urlBuffer.capacity());
			}
			urlBuffer.append(preUrl).append(i).append(postUrl);
			// 获取一页数据
			document = myHttpClient.get(urlBuffer.toString());
			// 解析列表
			List<Entity58> parseList = (List<Entity58>) parser.parseList(document);
			for (Entity58 item : parseList) {
				//访问、解析企业页面
				if (item.getCompanyUrl() != null) {
					document = myHttpClient.get(item.getCompanyUrl());
					parseResult = parser.parseDet(document, item);
				}
				//访问、解析文章页面
				if (item.getArticleUrl() != null) {
					document = myHttpClient.get(item.getArticleUrl());
					parseResult = parser.parseDet2(document, item);
				}
//				System.out.println(parseResult);
				excel.insertRow(Translate.toArray(parseResult));
				excel.saveFile();
			}
		}
		// 数据存档
		excel.saveFile();
	}
}
