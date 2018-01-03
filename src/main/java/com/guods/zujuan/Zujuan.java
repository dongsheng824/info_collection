package com.guods.zujuan;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class Zujuan {
	
	private static final String DETAIL_URL = "https://zujuan.21cnjy.com/question/detail/";
	private static final String PRE_URL = "https://zujuan.21cnjy.com/question/list?knowledges=13&question_channel_type=&difficult_index=&exam_type=&kid_num=&grade_id%5B%5D=0&grade_id%5B%5D=7&grade_id%5B%5D=8&grade_id%5B%5D=9&sortField=time&filterquestion=0&_=1514882171255&page=";
	private static final String COOKIES = "_csrf=fb0d294bcd70b24307ed481a9ffbca7f6e96af140aaa74a07a775c3d3673af13a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22A6oXBwh-OKHG-pfjuhbfyZzICjMD9eNK%22%3B%7D; Hm_lvt_5d70f3704df08b4bfedf4a7c4fb415ef=1514878142; chid=9f22ba4715c9a6998a74ca46f669cbdf971fdc8e71df933fca465c01ce29d004a%3A2%3A%7Bi%3A0%3Bs%3A4%3A%22chid%22%3Bi%3A1%3Bs%3A1%3A%223%22%3B%7D; xd=053c08991644b3be1f1cce76412b1634d2eb9867b529f5e7d3129bc291581e9ba%3A2%3A%7Bi%3A0%3Bs%3A2%3A%22xd%22%3Bi%3A1%3Bs%3A1%3A%222%22%3B%7D; Hm_lpvt_5d70f3704df08b4bfedf4a7c4fb415ef=1514881672";
	private static final FileManager fileManager = new FileManager("D:/zujuan");
	private static final String fileName = "数与式_有理数_正数和负数";
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver2.34.exe");
		Map<Integer, Integer> sections = new HashMap<Integer, Integer>();
		//////////////这里增加页码段////////////////////
		sections.put(1, 2);
		////////////////////////////////////////////
		int i = 1;
		for (final Entry<Integer, Integer> item : sections.entrySet()) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						runCatch(PRE_URL, COOKIES, item.getKey(), item.getValue());
					} catch (JsonParseException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			thread.setName("浏览器-" + i);
			thread.start();
			i++;
		}
	}

	private static void runCatch(String url, String cookies, Integer begin, Integer end) throws JsonParseException, JsonMappingException, IOException {
		List<Long> questionList;
		Scrow scrow = new Scrow();
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean tag = true;
		for(int i = begin; i <= end; i++) {
			//取每页id列表
			questionList = JsonGet.getQuestionList(url + i, cookies);
			System.out.println("第" + i + "页");
			System.out.println(questionList);
			if (questionList == null && questionList.size() == 0) {
				break;
			}
			//循环id列表取题目详情
			for (Long questionId : questionList) {
				scrow.getQuestion(fileManager, webDriver, DETAIL_URL + questionId, fileName);
			}
		}
	}
	
}
