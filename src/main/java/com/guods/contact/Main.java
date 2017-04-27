package com.guods.contact;

import javax.swing.SwingUtilities;

import com.guods.view.MainView;

public class Main 
{
    public static void main( String[] args )
    {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainView();
			}
		});
//    	//////////////////////////////////
//    	MyHttpClient myHttpClient = new MyHttpClient();//list-noimg,list-img
//    	Document document = myHttpClient.get("http://hz.ganji.com/jiatingzhuangxiu/o10");
//    	PageParser parser = new PageParser();
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
    }
    
}
