package com.guods.contact;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 这里简单使用Jsoup.connect()访问url
 * 扩展：也可以创建一个connection，设置长连接Connection:keep-alive，全部页面访问完成后再关闭connection，效率更高
 * @author guods
 *
 */
public class MyHttpClient {
	
	public Document get(String url){
		try {
			return Jsoup.connect(url)
					.get();
		} catch (HttpStatusException e) {
			//处理重定向地址错误问题，在异常中获取地址，处理地址重新发送请求
			try {
				String exUrl = e.getUrl();
				return Jsoup.connect(exUrl.substring(0, exUrl.indexOf("?"))).get();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Document jsonGet(String url){
		Map<String, String> cookies = new HashMap<String, String>();
		cookies.put("uuid", "w:d7dcefd0fde74bc8a90c0667364c5f30");
		cookies.put("UM_distinctid", "15b23c7417a1ed-07781e3212b944-6a11157a-1fa400-15b23c7417b22b");
		cookies.put("utm_source", "toutiao");
		cookies.put("tt_webid", "57234957546");
		cookies.put("_ga", "GA1.2.1673071305.1490953913");
		try {
			return Jsoup.connect(url)
					.ignoreContentType(true)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.cookies(cookies)
					.get();
		}catch (HttpStatusException e0){
			System.out.println(e0.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
