package com.guods.zujuan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
	
	public boolean getQuestion(FileManager fileManager, WebDriver webDriver, String url, String fileName){
		String paperFile;
		webDriver.get(url);
		WebElement exam = null;
		try {
			exam = webDriver.findElement(By.className("search-exam"));
		} catch (NoSuchElementException e) {
			String currentUrl = webDriver.getCurrentUrl();
			if (currentUrl.contains("verify.php")) {
				System.out.println("出现验证码！    " + url);
				return false;
			}
			System.out.println(Thread.currentThread().getName() + "----:题目不存在：" + url);
			return true;
		}
		paperFile = fileManager.getPaperFile(fileName + ".txt");
		//
		synchronized (fileManager) {
			appendToFile(fileManager, webDriver, paperFile, exam);
		}
		return true;
	}

	private void appendToFile(FileManager fileManager, WebDriver webDriver, String paperFile,
			WebElement exam) {
		String head;
		String content;
		String answer;
		//题目头
		WebElement examHead = exam.findElement(By.className("exam-head-left"));
		head = examHead.getAttribute("outerHTML");
		head = noiseFilter(head);
		head = dealImages(fileManager, head);
		fileManager.appendContent(paperFile, head);
		fileManager.appendContent(paperFile, System.lineSeparator());
		//题目内容
		WebElement examCon = exam.findElement(By.className("exam-con"));
		content = examCon.getAttribute("outerHTML");
		content = dealImages(fileManager, content);
		fileManager.appendContent(paperFile, content);
		fileManager.appendContent(paperFile, System.lineSeparator());
		//答案和解析
		fileManager.appendContent(paperFile, "---答案和解析---");
		fileManager.appendContent(paperFile, System.lineSeparator());
		WebElement examAnswer = exam.findElement(By.className("analyticbox-brick"));
		answer = examAnswer.getAttribute("outerHTML");
		answer = dealImages(fileManager, answer);
		fileManager.appendContent(paperFile, answer);
		fileManager.appendContent(paperFile, System.lineSeparator());
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
	
	public Set<String> extractImageUrls(String html){
		Set<String> urlList = new HashSet<String>();
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
		html = html.replace("&amp;", "&");
		Set<String> imageUrls = extractImageUrls(html);
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
