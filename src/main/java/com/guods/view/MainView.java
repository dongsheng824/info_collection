package com.guods.view;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jsoup.nodes.Document;

import com.guods.contact.Excel;
import com.guods.contact.MyHttpClient;
import com.guods.contact.PageParser;

public class MainView extends JFrame {

	private JTextField preUrl, postUrl, filePath, fileName1, fileName2, pages;
	private JButton button;
	private JTextArea outPut;
	JScrollPane scroll;

	public MainView() throws HeadlessException {
		super("数据采集器");
		initElements();
		initLayout();
		//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 400);
		setResizable(false);
		setVisible(true);
	}

	private void initElements() {
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
						startWork(Integer.valueOf(pageArray[0]), Integer.valueOf(pageArray[1]), preUrl.getText(), postUrl.getText(), filePath.getText(), 
								fileName1.getText(), fileName2.getText());
						button.setText("重新采集");
						button.setEnabled(true);
						button.paintImmediately(getBounds());
					}
				}).start();
			}
		});
		outPut = new JTextArea(10, 30);
		outPut.setEditable(true);
//		outPut.setLineWrap(true);
		scroll = new JScrollPane(outPut);
	}

	private void initLayout() {
		setLayout(new FlowLayout());
		Box labelBox, textBox, outPutBox, boxBase;
		boxBase = Box.createHorizontalBox();
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

		outPutBox = Box.createVerticalBox();
		outPutBox.add(button);
		outPutBox.add(Box.createVerticalStrut(8));
		outPutBox.add(scroll);
		boxBase.add(labelBox);
		boxBase.add(Box.createHorizontalStrut(8));
		boxBase.add(textBox);
		boxBase.add(Box.createHorizontalStrut(8));
		boxBase.add(outPutBox);
		add(boxBase);
	}

	public void startWork(int fromPage, int endPage, String preUrl, String postUrl,
			String filePath, String fileName1, String fileName2){
    	StringBuffer urlBuffer = new StringBuffer();
//    	String postUrl = "/?PGTID=0d300078-0004-f66b-fd74-1a60792d6344&ClickID=1";
    	MyHttpClient myHttpClient = new MyHttpClient();
    	Document document;
    	List<String> companyList;
    	//创建excel文档
    	String[] columnNames = {"标题", "描述", "名字", "联系方式"};
    	Excel excel = new Excel(filePath, fileName1, "contact", columnNames);
    	String[] companyColumnNames = {"公司名", "客户名", "电子邮箱", "联系电话1", "联系电话2", "QQ", "地址"};
    	Excel companyExcel = new Excel(filePath, fileName2, "companyContact", companyColumnNames);
    	//创建一个解析器
    	PageParser parser = new PageParser();
    	//修改页码拼接请求，一次循环处理一个页面
    	for (int i = fromPage; i <= endPage; i++) {
    		//清空urlBuffer，重新组装url
    		if (urlBuffer.capacity() > 0) {
    			urlBuffer.delete(0, urlBuffer.capacity());
			}
    		urlBuffer.append(preUrl).append(i).append(postUrl);
    		//访问url，获取document
    		document = myHttpClient.get(urlBuffer.toString());
    		//解析document，并获得公司页面列表
    		companyList = parser.parseListPage(document, excel);
    		outPut.append(System.lineSeparator() + "第" + i + "页非会员采集完成。。。");
			outPut.paintImmediately(getBounds());
    		
    		//如果有公司url，读取公司页面，解析公司页面获取公司页面的联系人信息
    		if (companyList != null && companyList.size() > 0) {
				for (String url : companyList) {
					//获取并解析公司页面
					if (url != null && url.trim() != "") {
						long time1 = System.currentTimeMillis();
						Document companyDocument = myHttpClient.get(url);
						long time2 = System.currentTimeMillis();
//						System.out.println("time1:" + (time2 - time1));
						int parserResult = parser.parseCompanyPage(companyDocument, companyExcel);
						if (parserResult == 0) {
							outPut.append(System.lineSeparator() + "会员企业网址获取联系人信息错误，请用浏览器打开网址查看！");
							outPut.append(System.lineSeparator() + url);
							outPut.paintImmediately(getBounds());
						}
						long time3 = System.currentTimeMillis();
//						System.out.println("time2:" + (time3 - time2));
					}
				}
			}
    		outPut.append(System.lineSeparator() + "第" + i + "页会员采集完成。。。");
			outPut.paintImmediately(getBounds());
		}
    	//数据存档
    	excel.saveFile();
    	companyExcel.saveFile();
    	outPut.append(System.lineSeparator() + 
    			"采集完成，非会员总共:" + excel.size() + "条，会员总共" + companyExcel.size() + "条！");
		outPut.paintImmediately(getBounds());
    }
}
