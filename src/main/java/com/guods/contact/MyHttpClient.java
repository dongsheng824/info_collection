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
}
