package com.guods.leleketang;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class LeleKetang {

	public static void main(String[] args) throws InterruptedException {
		final FileManager fileManager = new FileManager("D:/leleke");
		final Scrow scrow = new Scrow();
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver2.34.exe");
		final String preUrl = "http://www.leleketang.com/lib/";
		final String postUrl = ".shtml";
		Map<Long, Long> sections = new HashMap<Long, Long>();
		//////////////这里增加题目段////////////////////
		sections.put(20032L, 20032L);
		////////////////////////////////////////////
		int i = 1;
		for (final Entry<Long, Long> item : sections.entrySet()) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					runCatch(fileManager, scrow, preUrl, postUrl, item.getKey(), item.getValue());
				}
			});
			thread.setName("浏览器-" + i);
			thread.start();
			i++;
		}
	}

	private static void runCatch(FileManager fileManager, Scrow scrow, String preUrl, String postUrl, long begin, long end) {
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean tag = true;
		for (long i = begin; i <= end && tag; i++) {
			tag = scrow.getQuestion(fileManager, webDriver, preUrl + i + postUrl);
		}
	}
}
