package com.guods.koolearn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scrow {

	private static String regex;
	private static Pattern pattern;
	static {
		regex = ".*>.*>.*>";
		pattern = Pattern.compile(regex);
	}
	public void getQuestion(FileManager fileManager, WebDriver webDriver, String url){
		String paperFile, question, answer;
		webDriver.get(url);
		WebElement iBread = webDriver.findElement(By.className("i-bread"));
		String fileName = iBread.getText();
		Matcher matcher = pattern.matcher(fileName);
		matcher.find();
		fileName = matcher.group(0);
		fileName = fileName.substring(0, fileName.lastIndexOf('>'))
				.replace(" ", "")
				.replace(">", "_");
		paperFile = fileManager.getPaperFile(fileName + ".txt");
		WebElement panel = webDriver.findElement(By.className("i-panel"));
		
		//
		WebElement content = panel.findElement(By.className("content"));
		question = panel.getAttribute("outerHTML");
		question = noiseFilter(question);
		question = dealImages(fileManager, question);
		fileManager.appendContent(paperFile, question);
		fileManager.appendContent(paperFile, System.lineSeparator());
		//
		List<WebElement> tabList = webDriver.findElements(By.className("i-tab"));
		fileManager.appendContent(paperFile, "---答案和解析---");
		for (WebElement tab : tabList) {
			content = tab.findElement(By.className("content"));
			answer = content.getAttribute("outerHTML");
			answer = noiseFilter(answer);
			answer = dealImages(fileManager, answer);
			fileManager.appendContent(paperFile, answer);
			fileManager.appendContent(paperFile, System.lineSeparator());
		}
		fileManager.appendContent(paperFile, "------------下一题------------");
		fileManager.appendContent(paperFile, System.lineSeparator());
	}
	
	public String getImage(FileManager fileManager, String imgUrl, String fileExt){
		String fileName = UUID.randomUUID().toString() + fileExt;
		FileOutputStream out = null;
		InputStream in = null;
		File file = null;
		try {
			URL url = new URL(imgUrl);  
			//链接网络地址  
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();  
			//获取链接的输出流  
			in = connection.getInputStream();  
			//创建文件，fileName为编码之前的文件名  
			file = fileManager.createImageFile(fileName);
			//根据输入流写入文件  
			out = new FileOutputStream(file);  
			int i = 0;  
			while((i = in.read()) != -1){  
			    out.write(i);  
			}  
			out.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return file.getAbsolutePath();
	}
	
	public List<String> extractImageUrls(String html){
		List<String> urlList = new ArrayList<String>();
		Document document = Jsoup.parse(html);
		Elements shares = document.getElementsByClass("share");
		if (shares != null) {
			shares.remove();
		}
		Elements sharebuttonbox = document.getElementsByClass("i_sharebuttonbox");
		if (sharebuttonbox != null) {
			sharebuttonbox.remove();
		}
		Elements imgList = document.getElementsByTag("img");
		if (imgList == null) {
			return urlList;
		}
		for (Element element : imgList) {
			urlList.add(element.attr("src"));
		}
		return urlList;
	}
	
	public String noiseFilter(String html){
		Document document = Jsoup.parse(html);
		Elements shares = document.getElementsByClass("share");
		if (shares != null) {
			shares.remove();
		}
		Elements sharebuttonbox = document.getElementsByClass("i_sharebuttonbox");
		if (sharebuttonbox != null) {
			sharebuttonbox.remove();
		}
		Element content3 = document.getElementById("i-tab-content3");
		if (content3 != null) {
			content3.remove();
		}
		return document.html();
	}
	
	public String dealImages(FileManager fileManager, String html){
		List<String> imageUrls = extractImageUrls(html);
		String imageFileName;
		String fileExt;
		if (imageUrls == null || imageUrls.size() == 0) {
			return html;
		}
		for (String imageUrl : imageUrls) {
			fileExt = imageUrl.substring(imageUrl.lastIndexOf("."));
			imageFileName = getImage(fileManager, "http:" + imageUrl, fileExt);
			html = html.replace(imageUrl, imageFileName);
		}
		return html;
	}
}
