package com.guods.contact;

import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;

public class Main 
{
    public static void main( String[] args )
    {
//    	SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new MainView();
//			}
//		});
    	//////////////////////////////////
//    	MyHttpClient myHttpClient = new MyHttpClient();//list-noimg,list-img
//    	Document document = myHttpClient.qichachaGet("http://www.qichacha.com/search_index?key=99999&ajaxflag=1&province=ZJ&city=4&p=1&statusCode=10&");
//    	System.out.println(document);
//    	PageParser parser = new PageParser();
//    	parser.parseQichacha(document, null);
//    	String[] columnNames = {"标题", "名称", "电话1", "电话2"};
//    	Excel excel = new Excel("D:\\result\\", "ganji.xlsx", "sheet1", columnNames);
//    	Map<String, String[]> parseGanjiList = parser.parseGanjiList(document, excel);
//    	System.out.println(parseGanjiList);
//    	
//    	for (Entry<String, String[]> item : parseGanjiList.entrySet()) {
//    		document = myHttpClient.get(item.getKey());
//    		parser.parseGanjiSecond(document, excel, item.getValue());
//		}
//    	excel.saveFile();
    	
    	Qichacha qichacha = new Qichacha();
    	qichacha.getData();
    }
    
}
