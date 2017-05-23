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
		} catch (Exception e){
			System.out.println(e.getMessage());
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
	
	public Document qichachaGet(String url){
		String cookies = "acw_tc=AQAAAClHqy0uFQEAJB15fcPqhrhSTRVZ; gr_user_id=20450478-428d-4e84-9e47-c8570c35f0e6; UM_distinctid=15c249977dc9ec-0e66f796822e56-323f5c0f-1fa400-15c249977ddf01; _uab_collina=149526265733670429219309; _umdata=486B7B12C6AA95F2C03AF16645E603B16EC0C3F786F9D0267F8603ABB8B5997E6AD6ACFF2D01FF50CD43AD3E795C914C583A8F88C6F7B8B88B863617091A8254; PHPSESSID=4j85auikbilq17grtrit6oabv3; CNZZDATA1254842228=538662836-1495258947-%7C1495275147; gr_session_id_9c1eb7420511f8b2=2f06a850-e7c0-45e8-a1cd-21f3bed5d89c";
		try {
			return Jsoup.connect(url)
					.ignoreContentType(true)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("Cookie", cookies)
					.header("Cache-Control", "max-age=0")
					.header("Upgrade-Insecure-Requests", "1")
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
