package com.guods.contact;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jsoup.nodes.Document;
/**
 * 企查查数据采集  网站分页最多只返回50页，超过500条的数据增加分类条件采集
 * @author guods
 *
 */
public class Qichacha {

	private String cookies = "gr_user_id=da6b44ff-c7a3-4320-8fa4-f82252c85eab; UM_distinctid=15c7b095a1c215-018739d6cf87a5-323f5c0f-1fa400-15c7b095a1d54d; _uab_collina=149671283573296622873972; acw_tc=AQAAAPW6aVPfUQcAU9gA2r5uyB6ezzzz; _umdata=2FB0BDB3C12E491D47CB952815999DF7528EEAF672E19B045B1EC7387D8759C084826A5F8AB97BA1CD43AD3E795C914CDA4D58097D6622DFC5B93341B38D408E; PHPSESSID=t2p4a0irc4rc45dkv9tt3svun6; CNZZDATA1254842228=70749650-1496707710-https%253A%252F%252Fwww.baidu.com%252F%7C1496913544; gr_session_id_9c1eb7420511f8b2=74367d30-dc62-40d2-914d-3600bcdf980a";
	public void getData(){
		String[] columnNames = {"内容", "URL"};
		Excel excel = new Excel("D:\\result\\", "企查查数据0610-F-6.xlsx", "sheet1", columnNames);
		try {
			JsoupHttpClient myHttpClient = new JsoupHttpClient();//list-noimg,list-img
			
			String url = "http://www.qichacha.com/search_index?key=装饰&ajaxflag=1&province=ZJ&city=1&tel=T";
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
//			//按年份和行业
//			for (int k = 0; k < industryCodes.length; k++) {
//				for (int j = 1; j < 51; j++) {
//					String newUrl = url + status[1] + "&startDate=2017" + industryCodes[k] + "&p=" + j;
//					System.out.println(newUrl);
//					document = myHttpClient.qichachaGet(newUrl, cookies);
//					boolean result = parser.parseQichacha(document, excel);
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					if (!result) {
//						break;
//					}
//				}
//			}
//			//按子行业
//			for (int j = 1; j < 51; j++) {
//				String newUrl = url + status[1] + "&subindustrycode=51" + "&startDate=2017" + "&industrycode=F" + "&p=" + j;
//				System.out.println(newUrl);
//				document = myHttpClient.qichachaGet(newUrl, cookies);
//				boolean result = parser.parseQichacha(document, excel);
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				if (!result) {
//					break;
//				}
//			}
			Map<String, Integer> indMap = new HashMap<String, Integer>();
			indMap.put(null, 7);
			//indMap.put("A", 1);
			//indMap.put("C", 44);
			indMap.put("H", 1);
			indMap.put("I", 5);
			indMap.put("J", 1);
			indMap.put("K", 2);
//			indMap.put("L", 43);
//			indMap.put("M", 38);
			indMap.put("N", 2);
			indMap.put("O", 43);
			//indMap.put("R", 2);
//			for (Entry<String, Integer> item : indMap.entrySet()) {
//			}
			for (int j = 222; j <= 224; j++) {
				String newUrl = url + status[1] + "&industrycode=F" + "&p=" + j;
				System.out.println(newUrl);
				document = myHttpClient.qichachaGet(newUrl, cookies);
				boolean result = parser.parseQichacha(document, excel);
				excel.saveFile();
				ThreadLocalRandom random = ThreadLocalRandom.current();
				try {
					Thread.sleep((long)(120*1000*random.nextFloat()));
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
		JsoupHttpClient myHttpClient = new JsoupHttpClient();
		PageParser pageParser = new PageParser();
		String url;
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Document document = null;
		for (int i = 0; i < size; i++) {
			url = "http://www.qichacha.com" + excel.readUnit(i, 0);
			resultExcel.writeUnit(i + 1, 0, url);
			try {
				document = myHttpClient.qichachaGet(url, cookies);
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
