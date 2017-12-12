package com.guods.contact;

import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 这里简单使用Jsoup.connect()访问url
 * 扩展：也可以创建一个connection，设置长连接Connection:keep-alive，全部页面访问完成后再关闭connection，效率更高
 * @author guods
 *
 */
public class JsoupHttpClient {
	
	public Document get(String url){
		try {
			return Jsoup.connect(url).get();
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
	
	public Document cookieGet(String url, String cookies){
		try {
			return Jsoup.connect(url)
					.ignoreContentType(true)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("Cookie", cookies)
					.get();
		}catch (HttpStatusException e0){
			e0.printStackTrace();
			System.out.println(e0.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Document qichachaGet(String url, String cookies){
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
