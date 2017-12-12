package com.guods.exam;

import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;

import com.guods.contact.ApachHttpClient;
import com.guods.contact.JsoupHttpClient;

public class Main 
{
    public static void main( String[] args )
    {
    	startWork(420, 440, "http://rc.k12.com.cn/cloudRes/search?q=数学&x=47&y=13&type=exam&page=", "D://k12//", 
    			"bdshare_firstime=1503132930588; symfony=7tt62089ers5389knusvb00fk5");
    }
    
    
    public static void startWork(int fromPage, int endPage, String preUrl, String filePath, String cookies) {
		JsoupHttpClient myHttpClient = new JsoupHttpClient();
		ApachHttpClient apachHttpClient = new ApachHttpClient();
		Document document;
		Map<String, String> downloadPara;
		K12Parser parser = new K12Parser();
		// 修改页码拼接请求，一次循环处理一个页面
		for (int i = fromPage; i <= endPage ; i++) {
			// 获取一页数据
			document = myHttpClient.cookieGet(preUrl + i, cookies);
			// 解析列表
			Map<String, String> urlListMap = (Map<String, String>) parser.parseList(document);
			for (Entry<String, String> entry : urlListMap.entrySet()) {
				document = myHttpClient.cookieGet(entry.getKey(), cookies);
				downloadPara = parser.parseDet(document);
				for (Entry<String, String> item : downloadPara.entrySet()) {
					apachHttpClient.download(item.getKey(), cookies, filePath + item.getValue());
					System.out.println(">>>-----" + item.getValue() + "已下载");
				}
			}
			System.out.println("第" + i + "页已下载完成！" );
		}
	}
}
