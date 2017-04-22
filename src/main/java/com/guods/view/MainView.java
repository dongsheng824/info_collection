package com.guods.view;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jsoup.nodes.Document;

import com.guods.contact.AsCpGenerator;
import com.guods.contact.Excel;
import com.guods.contact.MyHttpClient;
import com.guods.contact.PageParser;
import com.guods.toutiao.model.AsCp;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField preUrl, postUrl, filePath, fileName1, fileName2, pages;
	private JButton button, stopButton;
	private JTextArea outPut;
	private JScrollPane scroll;

	private JTextField toutiaoCount, toutiaoFilePath, toutiaoFileName, toutiaoCommCount, toutiaoKeyword;
	private JButton toutiaoButton, toutiaoStopButton, toutiaoSearchButton;
	private JTextArea toutiaoOutPut;
	private JScrollPane toutiaoScroll;
	private JComboBox toutiaoType;
	
	private boolean stopToutiao = false;
	private boolean stop58 = false;
	
	public MainView() throws HeadlessException {
		super("数据采集神器");
		init58Elements();
		initToutiaoElements();
		initLayout();
		//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 700);
		setResizable(false);
		setVisible(true);
	}

	private void init58Elements() {
		preUrl = new JTextField(20);
		preUrl.setText("http://hz.58.com/zhuangxiu/pn");
		postUrl = new JTextField(20);
		postUrl.setText("/?PGTID=0d300078-0004-f66b-fd74-1a60792d6344&ClickID=1");
		pages = new JTextField(20);
		pages.setText("1-20");
		filePath = new JTextField(20);
		filePath.setText("d:\\result\\");
		filePath.setEditable(false);
		fileName1 = new JTextField(20);
		fileName1.setText("context.xlsx");
		fileName2 = new JTextField(20);
		fileName2.setText("companyContext.xlsx");

		button = new JButton("开始采集");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						button.setText("采集中...");
						button.paintImmediately(getBounds());
						button.setEnabled(false);
						String[] pageArray = (pages.getText()).trim().split("-");
						outPut.setText("开始采集数据：");
						outPut.paintImmediately(getBounds());
						startWork(Integer.valueOf(pageArray[0]), Integer.valueOf(pageArray[1]), preUrl.getText(),
								postUrl.getText(), filePath.getText(), fileName1.getText(), fileName2.getText());
						button.setText("重新采集");
						button.setEnabled(true);
						button.paintImmediately(getBounds());
					}
				}).start();
			}
		});
		
		stopButton = new JButton("停止");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop58 = true;
			}
		});
		
		outPut = new JTextArea(10, 30);
		outPut.setEditable(true);
		scroll = new JScrollPane(outPut);
	}

	@SuppressWarnings("unchecked")
	private void initToutiaoElements() {
		toutiaoType = new JComboBox();
		toutiaoType.addItem("首页");
		toutiaoType.addItem("家居");
		toutiaoType.addItem("社会");
		toutiaoType.addItem("搞笑");
		toutiaoType.addItem("热点");
		toutiaoKeyword = new JTextField(20);
		toutiaoKeyword.setText("装修");
		toutiaoCount = new JTextField(20);
		toutiaoCount.setText("20000");
		toutiaoFilePath = new JTextField(20);
		toutiaoFilePath.setText("d:\\result\\");
		toutiaoFilePath.setEditable(false);
		toutiaoFileName = new JTextField(20);
		toutiaoFileName.setText("今日头条.xlsx");
		toutiaoCommCount = new JTextField(20);
		toutiaoCommCount.setText("50");
		
		toutiaoButton = new JButton("开始采集");
		toutiaoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					String url = "http://www.toutiao.com/api/pc/feed/?category=__all__&utm_source=toutiao&widen=1&max_behot_time=1491359690&max_behot_time_tmp=1491359690&tadrequire=false&as=A195286EB455C9D&cp=58E4356CA92D7E1";
					public void run() {
						if (toutiaoType.getSelectedItem().toString().equals("首页")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=__all__&utm_source=toutiao&widen=1&max_behot_time=1491359690&max_behot_time_tmp=1491359690&tadrequire=false&as=A195286EB455C9D&cp=58E4356CA92D7E1";
						};
						if (toutiaoType.getSelectedItem().toString().equals("家居")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=news_home&utm_source=toutiao&widen=1&max_behot_time=1491352754&max_behot_time_tmp=1491352754&tadrequire=true&as=A155286E14A5718&cp=58E4F5674108DE1";
						};
//						if (toutiaoType.getSelectedItem().toString().equals("段子")) {
//							url = "http://www.toutiao.com/api/article/feed/?category=essay_joke&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A10548FE0485C41&cp=58E4855C44F10E1";
//						};
						if (toutiaoType.getSelectedItem().toString().equals("搞笑")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=funny&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A165D8CEB4D5C6F&cp=58E495BC567F4E1";
						};
						if (toutiaoType.getSelectedItem().toString().equals("社会")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=news_society&utm_source=toutiao&widen=1&max_behot_time=1491357717&max_behot_time_tmp=1491357717&tadrequire=true&as=A1A5C87ED4B5E29&cp=58E4954EE2397E1";
						};
						if (toutiaoType.getSelectedItem().toString().equals("热点")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=news_hot&utm_source=toutiao&widen=1&max_behot_time=1491360111&max_behot_time_tmp=1491360111&tadrequire=true&as=A1D5D84E4415E81&cp=58E4C5FEE8715E1";
						};
						toutiaoButton.setText("采集中...");
						toutiaoButton.paintImmediately(getBounds());
						toutiaoButton.setEnabled(false);
						toutiaoOutPut.setText("今日头条开始采集数据。。。");
						toutiaoOutPut.paintImmediately(getBounds());
						startWorkToutiao(Integer.valueOf(toutiaoCount.getText()), 
								url, 
								toutiaoFilePath.getText(), toutiaoFileName.getText(), Integer.valueOf(toutiaoCommCount.getText()),
								toutiaoKeyword.getText());
						toutiaoButton.setText("重新采集");
						toutiaoButton.setEnabled(true);
						toutiaoButton.paintImmediately(getBounds());
					}
				}).start();
			}
		});
		
		toutiaoSearchButton = new JButton("开始搜索");
		toutiaoSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					String preUrl = "http://www.toutiao.com/search_content/?format=json&autoload=true&count=20&cur_tab=1";
					public void run() {
						toutiaoSearchButton.setText("采集中...");
						toutiaoSearchButton.paintImmediately(getBounds());
						toutiaoSearchButton.setEnabled(false);
						toutiaoOutPut.setText("今日头条开始搜索数据。。。");
						toutiaoOutPut.paintImmediately(getBounds());
						startSearchToutiao(preUrl, Integer.valueOf(toutiaoCount.getText()),
								toutiaoFilePath.getText(), toutiaoFileName.getText(), 
								Integer.valueOf(toutiaoCommCount.getText()),
								toutiaoKeyword.getText());
						toutiaoSearchButton.setText("重新搜索");
						toutiaoSearchButton.setEnabled(true);
						toutiaoSearchButton.paintImmediately(getBounds());
					}
				}).start();
			}
		});
		
		toutiaoStopButton = new JButton("停止");
		toutiaoStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopToutiao = true;
			}
		});
		
		toutiaoOutPut = new JTextArea(10, 30);
		toutiaoOutPut.setEditable(true);
		toutiaoScroll = new JScrollPane(toutiaoOutPut);
	}
	
	private void initLayout() {
		setLayout(new FlowLayout());
		Box boxContainer;
		boxContainer = Box.createVerticalBox();
		//添加58
		initLayout58(boxContainer);
		//添加头条
		initLayoutToutiao(boxContainer);
		add(boxContainer);
	}

	private void initLayout58(Box boxContainer) {
		Box labelBox, textBox, outPutBox, box, header, buttonBox;
		header = Box.createHorizontalBox();
		header.add(new JLabel("58同城采集"));
		box = Box.createHorizontalBox();
		labelBox = Box.createVerticalBox();
		labelBox.add(new JLabel("URL前缀："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("URL后缀："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("页码区间："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("存档文件夹："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("非会员文件名："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("会员文件名："));
		textBox = Box.createVerticalBox();
		textBox.add(preUrl);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(postUrl);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(pages);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(filePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName1);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName2);

		buttonBox = Box.createHorizontalBox();
		buttonBox.add(button);
		buttonBox.add(Box.createHorizontalStrut(20));
		buttonBox.add(stopButton);
		
		outPutBox = Box.createVerticalBox();
		outPutBox.add(buttonBox);
		outPutBox.add(Box.createVerticalStrut(8));
		outPutBox.add(scroll);
		box.add(labelBox);
		box.add(Box.createHorizontalStrut(8));
		box.add(textBox);
		box.add(Box.createHorizontalStrut(8));
		box.add(outPutBox);
		//添加栏目
		boxContainer.add(header);
		boxContainer.add(box);
	}
	
	private void initLayoutToutiao(Box boxContainer) {
		Box labelBox, textBox, outPutBox, box, header, buttonBox, buttonBoxSub;
		header = Box.createHorizontalBox();
		header.add(new JLabel("今日头条采集"));
		box = Box.createHorizontalBox();
		labelBox = Box.createVerticalBox();
		labelBox.add(new JLabel("头条类型："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("搜索关键词："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("采集条数："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("评论量："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("存档文件夹："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("存档文件名："));
		textBox = Box.createVerticalBox();
		textBox.add(toutiaoType);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(toutiaoKeyword);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(toutiaoCount);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(toutiaoCommCount);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(toutiaoFilePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(toutiaoFileName);

		buttonBoxSub = Box.createVerticalBox();
		buttonBoxSub.add(toutiaoButton);
		buttonBoxSub.add(Box.createVerticalStrut(8));
		buttonBoxSub.add(toutiaoSearchButton);
		
		buttonBox = Box.createHorizontalBox();
		buttonBox.add(buttonBoxSub);
		buttonBox.add(Box.createHorizontalStrut(20));
		buttonBox.add(toutiaoStopButton);
		
		outPutBox = Box.createVerticalBox();
		outPutBox.add(buttonBox);
		outPutBox.add(Box.createVerticalStrut(8));
		outPutBox.add(toutiaoScroll);
		box.add(labelBox);
		box.add(Box.createHorizontalStrut(8));
		box.add(textBox);
		box.add(Box.createHorizontalStrut(8));
		box.add(outPutBox);
		//
		boxContainer.add(header);
		boxContainer.add(box);
	}

	public void startWork(int fromPage, int endPage, String preUrl, String postUrl, String filePath, String fileName1,
			String fileName2) {
		StringBuffer urlBuffer = new StringBuffer();
		MyHttpClient myHttpClient = new MyHttpClient();
		Document document;
		List<String> companyList;
		// 创建excel文档
		String[] columnNames = { "标题", "描述", "名字", "联系方式" };
		Excel excel = new Excel(filePath, fileName1, "contact", columnNames);
		String[] companyColumnNames = { "公司名", "客户名", "电子邮箱", "联系电话1", "联系电话2", "QQ", "地址" };
		Excel companyExcel = new Excel(filePath, fileName2, "companyContact", companyColumnNames);
		// 创建一个解析器
		PageParser parser = new PageParser();
		stop58 = false;
		// 修改页码拼接请求，一次循环处理一个页面
		for (int i = fromPage; i <= endPage && !stop58; i++) {
			// 清空urlBuffer，重新组装url
			if (urlBuffer.capacity() > 0) {
				urlBuffer.delete(0, urlBuffer.capacity());
			}
			urlBuffer.append(preUrl).append(i).append(postUrl);
			// 访问url，获取document
			document = myHttpClient.get(urlBuffer.toString());
			// 解析document，并获得公司页面列表
			companyList = parser.parseListPage(document, excel);
			outPut.append(System.lineSeparator() + "第" + i + "页非会员采集完成。。。");
			outPut.paintImmediately(getBounds());

			// 如果有公司url，读取公司页面，解析公司页面获取公司页面的联系人信息
			if (companyList != null && companyList.size() > 0) {
				for (String url : companyList) {
					if (stop58) {
						break;
					}
					// 获取并解析公司页面
					if (url != null && url.trim() != "") {
						// long time1 = System.currentTimeMillis();
						Document companyDocument = myHttpClient.get(url);
						// long time2 = System.currentTimeMillis();
						// System.out.println("time1:" + (time2 - time1));
						int parserResult = parser.parseCompanyPage(companyDocument, companyExcel);
						if (parserResult == 0) {
							outPut.append(System.lineSeparator() + "会员企业网址获取联系人信息错误，请用浏览器打开网址查看！");
							outPut.append(System.lineSeparator() + url);
							outPut.paintImmediately(getBounds());
							continue;
						}
						if (parserResult == 2) {
							outPut.append(System.lineSeparator() + "已产生验证码，请用浏览器打开网址输入验证码！");
							outPut.append(System.lineSeparator() + url);
							outPut.paintImmediately(getBounds());
						}
						// long time3 = System.currentTimeMillis();
						// System.out.println("time2:" + (time3 - time2));
					}
				}
			}
			outPut.append(System.lineSeparator() + "第" + i + "页会员采集完成。。。");
			outPut.paintImmediately(getBounds());
		}
		stop58 = false;
		// 数据存档
		excel.saveFile();
		companyExcel.saveFile();
		outPut.append(System.lineSeparator() + "采集完成，非会员总共:" + excel.size() + "条，会员总共" + companyExcel.size() + "条！");
		outPut.paintImmediately(getBounds());
	}

	public void startWorkToutiao(int count, String url, String filePath, String fileName, int toutiaoCommCount, String keyword) {
		StringBuffer urlBuffer = new StringBuffer();
		MyHttpClient myHttpClient = new MyHttpClient();
		String preUrl;
		Document document;
		boolean tag;
		// String maxBehotTime = url.substring(url.indexOf("&max_behot_time") +
		// 16, url.indexOf("&max_behot_time_tmp"));
		Long maxBehotTime = System.currentTimeMillis()/1000;
		// 创建excel文档
		String[] columnNames = { "标题", "URL", "评论数量" };
		Excel excel = new Excel(filePath, fileName, "contact", columnNames);
		// 创建一个解析器
		PageParser parser = new PageParser();
		// 修改页码拼接请求，一次循环处理一个页面
		int index = url.indexOf("&max_behot_time");
		preUrl = url.substring(0, index);
		int i = 0;
		AsCp asCp = AsCpGenerator.generate(((Long)(System.currentTimeMillis())).toString());
		stopToutiao = false;
		try {
			while (!stopToutiao && excel.size() < count) {
				System.out.println(i);
				// 清空urlBuffer，重新组装url
				if (urlBuffer.capacity() > 0) {
					urlBuffer.delete(0, urlBuffer.capacity());
				}
				urlBuffer.append(preUrl).append("&max_behot_time=").append(maxBehotTime)
					.append("&max_behot_time_tmp=").append(maxBehotTime)
					.append("&tadrequire=true")
					.append("&as=").append(asCp.getAs()).append("&cp=").append(asCp.getCp());
				System.out.println(urlBuffer.toString());
				document = myHttpClient.jsonGet(urlBuffer.toString());
				tag = parser.parseToutiao(document, excel, toutiaoCommCount, false, keyword);
				if (tag) {
					toutiaoOutPut.append(System.lineSeparator() + "已采到:" + (excel.size() - 1) + "条");
					toutiaoOutPut.paintImmediately(getBounds());
				}
				maxBehotTime = maxBehotTime - 1000;
				i++;
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stopToutiao = false;
		// 数据存档
		excel.saveFile();
		toutiaoOutPut.append(System.lineSeparator() + "头条采集完成，总共:" + excel.size() + "条");
		toutiaoOutPut.paintImmediately(getBounds());
	}
	
	public void startSearchToutiao(String preUrl, int count, String filePath, String fileName, int toutiaoCommCount, String keyword) {
		StringBuffer urlBuffer = new StringBuffer();
		MyHttpClient myHttpClient = new MyHttpClient();
		Document document;
		boolean hasMore = true;
		// 创建excel文档
		String[] columnNames = { "标题", "URL", "评论数量" };
		Excel excel = new Excel(filePath, fileName, "contact", columnNames);
		// 创建一个解析器
		PageParser parser = new PageParser();
		int i = 0;
		stopToutiao = false;
		try {
			while (!stopToutiao && excel.size() < count && hasMore) {
				System.out.println(i);
				// 清空urlBuffer，重新组装url
				if (urlBuffer.capacity() > 0) {
					urlBuffer.delete(0, urlBuffer.capacity());
				}
				urlBuffer.append(preUrl).append("&offset=").append(i * 20)
					.append("&keyword=").append(keyword);
				System.out.println(urlBuffer.toString());
				document = myHttpClient.jsonGet(urlBuffer.toString());
				hasMore = parser.parseToutiao(document, excel, toutiaoCommCount, true, keyword);
				if (hasMore) {
					toutiaoOutPut.append(System.lineSeparator() + "已采到:" + (excel.size() - 1) + "条");
					toutiaoOutPut.paintImmediately(getBounds());
				}
				i++;
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stopToutiao = false;
		// 数据存档
		excel.saveFile();
		toutiaoOutPut.append(System.lineSeparator() + "头条采集完成，总共:" + excel.size() + "条");
		toutiaoOutPut.paintImmediately(getBounds());
	}
}
