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
		String cookies = "acw_tc=AQAAAKcM/2ttXgoAGZDqemtMH8c4rbzS; UM_distinctid=15c157eec3f8-0d178768b03694-323f5c0f-1fa400-15c157eec4097b; _uab_collina=149500925904404503538663; gr_user_id=a3bb30b3-c8aa-4310-8128-79ded86bc298; _umdata=486B7B12C6AA95F2F011E74167B6A7C10B3A099608ADF66F4294A5630952F38B851E8050B835297FCD43AD3E795C914C6B665995F5FBB80D1198CED4B011A83A; PHPSESSID=gk1eh5h0m60rt4l1157ocoafm6; CNZZDATA1254842228=1174307543-1495004895-%7C1495004895; gr_session_id_9c1eb7420511f8b2=e22cf959-4423-4ee4-8880-d7c18bb90d60";
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
