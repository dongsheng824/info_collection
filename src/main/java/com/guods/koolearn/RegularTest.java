package com.guods.koolearn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTest {

	public static void main(String[] args) {
		String target = "新东方在线题库 >初中数学试题 >绝对值 >如果a<0  > ddd >  >  ddd >";
//		String regex = "[\u0391-\uFFE5a-zA-z0-9\\s]+>[\u0391-\uFFE5a-zA-z0-9\\s]+>[\u0391-\uFFE5a-zA-z0-9\\s]+>";
		String regex = "[^>]*>[^>]*>[^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		matcher.find();
		System.out.println(matcher.group(0));
	}
}
