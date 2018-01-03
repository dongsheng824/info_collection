package com.guods.leleketang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scrow {

	private static String regex, regrex2;
	private static Pattern pattern, pattern2;
	static {
		regex = ".*>.*>";
		pattern = Pattern.compile(regex);
		
		regrex2 = ".*>";
		pattern2 = Pattern.compile(regrex2);
	}
	public boolean getQuestion(FileManager fileManager, WebDriver webDriver, String url){
		String paperFile, question, answer;
		webDriver.get(url);
		WebElement breadcrumb = null;
		
		try {
			breadcrumb = webDriver.findElement(By.className("breadcrumb"));
		} catch (NoSuchElementException e) {
			String currentUrl = webDriver.getCurrentUrl();
			if (currentUrl.contains("verify.php")) {
				System.out.println("出现验证码！    " + url);
				return false;
			}
			System.out.println(Thread.currentThread().getName() + "----:题目不存在：" + url);
			return true;
		}
		String fileName = breadcrumb.getText();
		Matcher matcher = pattern.matcher(fileName);
		if( matcher.find()){
			fileName = matcher.group(0);
		}else {
			matcher = pattern2.matcher(fileName);
			if (matcher.find()) {
				fileName = matcher.group(0);
			}else {
				return true;
			}
		};
		fileName = fileName.substring(0, fileName.lastIndexOf('>'))
				.replace(" ", "")
				.replace(">", "_")
				.replace("<", "")
				.replace("知识点", "");
		paperFile = fileManager.getPaperFile(fileName + ".txt");
		
		List<WebElement> questionContentList = webDriver.findElements(By.className("question_part_content"));
		//
		synchronized (fileManager) {
			appendToFile(fileManager, webDriver, paperFile, questionContentList);
		}
		return true;
	}

	private void appendToFile(FileManager fileManager, WebDriver webDriver, String paperFile,
			List<WebElement> questionContentList) {
		String question;
		String answer;
		WebElement content = questionContentList.get(0).findElement(By.className("uc_q"));
		question = content.getAttribute("outerHTML");
		question = noiseFilter(question);
		question = dealImages(fileManager, question);
		fileManager.appendContent(paperFile, question);
		fileManager.appendContent(paperFile, System.lineSeparator());
		//
		WebElement questionPart2 = webDriver.findElement(By.className("question_part_content2"));
		fileManager.appendContent(paperFile, questionPart2.getAttribute("outerHTML"));
		//
		fileManager.appendContent(paperFile, "---答案和解析---");
		List<WebElement> clearfix = questionContentList.get(1).findElements(By.className("clearfix"));
		for (WebElement element : clearfix) {
			if (element.getText().contains("分析") || element.getText().contains("解答") || element.getText().contains("点评")) {
				answer = element.getAttribute("outerHTML");
				answer = noiseFilter(answer);
				answer = dealImages(fileManager, answer);
				fileManager.appendContent(paperFile, answer);
				fileManager.appendContent(paperFile, System.lineSeparator());
			}
		}
		fileManager.appendContent(paperFile, "------------下一题------------");
		fileManager.appendContent(paperFile, System.lineSeparator());
	}
	
	public String getImage(FileManager fileManager, String imgUrl, String fileExt){
		String fileName = UUID.randomUUID().toString() + fileExt;
		FileOutputStream out = null;
		InputStream in = null;
		File file = null;
		String baseUrl = "http://www.leleketang.com";
		try {
			URL url = new URL(baseUrl + imgUrl);  
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			URL url;
			try {
				url = new URL(imgUrl);
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (file != null) {
			return file.getAbsolutePath();
		}else {
			return null;
		}
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
		String str;
		for (Element element : imgList) {
			str = element.attr("src").trim();
			if (str != null && str != "") {
				urlList.add(element.attr("src").trim());
			}
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
			if (fileExt.contains("?")) {
				fileExt = fileExt.substring(0, fileExt.indexOf("?"));
			}
			imageFileName = getImage(fileManager, imageUrl, fileExt);
			if (imageFileName != null) {
				html = html.replace(imageUrl, imageFileName);
			}
		}
		return html;
	}
}
