package com.guods.koolearn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;


public class DealTextFile {
	
	public static void main(String[] args) {
		String filePath = "D:/本地批量处理/新东方在线题库_初中数学试题_反比例函数性质.txt";
		String dir = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		String baseFileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
		String keyword = filePath.substring(filePath.lastIndexOf("_") + 1, filePath.lastIndexOf("."));
		
		File file;
		File newFile;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			file = new File(filePath);
			//读文件
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			//处理文件
			String newTxt = stringBuffer.toString();
			
			Pattern pattern = Pattern.compile("<html>");
			newTxt = pattern.matcher(newTxt).replaceAll("");
			
			pattern = Pattern.compile("<head></head>");
			newTxt = pattern.matcher(newTxt).replaceAll("");
			
			pattern = Pattern.compile("<body>");
			newTxt = pattern.matcher(newTxt).replaceAll("");
			
			pattern = Pattern.compile("</html>");
			newTxt = pattern.matcher(newTxt).replaceAll("");
			
			pattern = Pattern.compile("</body>");
			newTxt = pattern.matcher(newTxt).replaceAll("");
			
			pattern = Pattern.compile("<span class=\"title\">题目</span>");
			newTxt = pattern.matcher(newTxt).replaceAll("");
			
			pattern = Pattern.compile("<div id=\"shareBtn\">     <div class=\"i-share-area g-clear ji-share-area\">        </div>    </div>");
			newTxt = pattern.matcher(newTxt).replaceAll("");

			pattern = Pattern.compile("<label> <span class=\"label\">题型：</span><span>");
			newTxt = pattern.matcher(newTxt).replaceAll("<p>@【题型】</p><p>");
			
			pattern = Pattern.compile("<label> <span class=\"label\">难度：</span><span>");
			newTxt = pattern.matcher(newTxt).replaceAll("<p>@【难度】</p><p>");
			
			pattern = Pattern.compile("<label> <span class=\"label\">来源：</span><span>");
			newTxt = pattern.matcher(newTxt).replaceAll("<p>@【来源】</p><p>");
			
			pattern = Pattern.compile("</span> </label>");
			newTxt = pattern.matcher(newTxt).replaceAll("</p>");
			
			pattern = Pattern.compile("<p>@【来源】</p>");
			newTxt = pattern.matcher(newTxt).replaceAll("<p>@【考点】</p><p>" + keyword + "</p><p>@【来源】</p>");
			
			pattern = Pattern.compile("<div class=\"content\">");
			newTxt = pattern.matcher(newTxt).replaceAll("<p>@【题目】</p><div class=\"content\">");
			
			pattern = Pattern.compile("---答案和解析---");
			newTxt = pattern.matcher(newTxt).replaceAll("<p>@【解答】</p>");
			//保存文件
			newFile = new File(dir + baseFileName + "_new.txt");
			fileWriter = new FileWriter(newFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(newTxt);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				bufferedReader.close();
				bufferedWriter.close();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
