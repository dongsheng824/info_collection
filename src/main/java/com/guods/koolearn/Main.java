package com.guods.koolearn;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Main {

	public static void main(String[] args) throws InterruptedException {
		FileManager fileManager = new FileManager("D:/koolearn");
		Scrow scrow = new Scrow();
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver2.29.exe");
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		String preUrl = "http://www.koolearn.com/shiti/st-1-";
		String postUrl = ".html";
		boolean result = true;
		for (int i = 589116; i <= 589116 && result; i++) {
			result = scrow.getQuestion(fileManager, webDriver, preUrl + i + postUrl);
		}
	}
}
