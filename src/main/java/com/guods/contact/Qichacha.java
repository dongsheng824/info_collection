package com.guods.contact;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.nodes.Document;
/**
 * 企查查数据采集  网站分页最多只返回50页，超过500条的数据增加分类条件采集
 * @author guods
 *
 */
public class Qichacha {

	public void getData(){
		String[] columnNames = {"内容", "URL"};
		Excel excel = new Excel("D:\\result\\", "企查查数据（续存2017）.xlsx", "sheet1", columnNames);
		try {
			MyHttpClient myHttpClient = new MyHttpClient();//list-noimg,list-img
			
			String url = "http://www.qichacha.com/search_index?key=装饰材料&ajaxflag=1&province=ZJ&city=4";
			//公司状态
			String[] status = {"&statusCode=10", "&statusCode=20", "&statusCode=60", "&statusCode=90", "&statusCode=99"};
			//公司年份
			String[] years = new String[35];
			for (int i = 0; i < years.length; i++) {
				int k = i + 1978;
				years[i] = "&startDate=" + k;
			}
			//行业类别"&industrycode=", "&industrycode=F",
			String[] industryCodes = {"&industrycode=A", "&industrycode=C", "&industrycode=E", 
					"&industrycode=G", "&industrycode=H", "&industrycode=I", "&industrycode=J", 
					"&industrycode=K", "&industrycode=L", "&industrycode=M", "&industrycode=N", "&industrycode=O", 
					"&industrycode=R", "&industrycode=S"};
			Document document;
			PageParser parser = new PageParser();
			//按年份
//			for (int i = 0; i < years.length; i++) {
//				for (int j = 1; j < 51; j++) {
//					String newUrl = url + status[1] + years[i] + "&p=" + j;
//					System.out.println(newUrl);
//					document = myHttpClient.qichachaGet(newUrl);
//			    	boolean result = parser.parseQichacha(document, excel);
//			    	try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//			    	if (!result) {
//						break;
//					}
//				}
//			}
			//按年份和行业
			for (int k = 0; k < industryCodes.length; k++) {
				for (int j = 1; j < 51; j++) {
					String newUrl = url + status[1] + "&startDate=2017" + industryCodes[k] + "&p=" + j;
					System.out.println(newUrl);
					document = myHttpClient.qichachaGet(newUrl);
					boolean result = parser.parseQichacha(document, excel);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!result) {
						break;
					}
				}
			}
			//按子行业
			for (int j = 1; j < 51; j++) {
				String newUrl = url + status[1] + "&subindustrycode=51" + "&startDate=2017" + "&industrycode=F" + "&p=" + j;
				System.out.println(newUrl);
				document = myHttpClient.qichachaGet(newUrl);
				boolean result = parser.parseQichacha(document, excel);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!result) {
					break;
				}
			}
			for (int j = 1; j < 51; j++) {
				String newUrl = url + status[1] + "&subindustrycode=52" + "&startDate=2017" + "&industrycode=F" + "&p=" + j;
				System.out.println(newUrl);
				document = myHttpClient.qichachaGet(newUrl);
				boolean result = parser.parseQichacha(document, excel);
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!result) {
					break;
				}
			}
		} finally{
			excel.saveFile();
		}
	}
	
	public void getRelContent(String filePath, String fileName, String resultPathName) throws InvalidFormatException, IOException{
		Excel excel = new Excel().readExcel(filePath, fileName);
		int size = excel.size();
		String[] columnNames = {"URL", "地址", "经营范围"};
		Excel resultExcel = new Excel(filePath, resultPathName, "result", columnNames);
		MyHttpClient myHttpClient = new MyHttpClient();
		PageParser pageParser = new PageParser();
		String url;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Document document = null;
		for (int i = 0; i < size; i++) {
			url = "http://www.qichacha.com" + excel.readUnit(i, 0);
			resultExcel.writeUnit(i + 1, 0, url);
			try {
				document = myHttpClient.qichachaGet(url);
			} catch (Exception e1) {
				System.out.println("url" + "：地址访问错误！");
				continue;
			}
			System.out.println("http://www.qichacha.com" + excel.readUnit(i, 0));
			pageParser.parseQichachaDet(document, resultExcel, i + 1);
			resultExcel.saveFile();
			try {
				Thread.sleep((long) (120*1000*random.nextFloat()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
