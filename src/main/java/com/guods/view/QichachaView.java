package com.guods.view;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

/**
 * 企查查采集页面
 * @author guods
 *
 */
public class QichachaView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField url, filePath, fileName, urlPages;
	private JButton button, stopButton;
	private JTextArea outPut;
	private JScrollPane scroll;
	private volatile boolean stop = false;
	
	public QichachaView() throws HeadlessException {
		super("企查查数据采集");
		//初始化组件
		initElements();
		//设置页面
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 400);
		setResizable(false);
		setVisible(true);
		//添加组件
		Box boxContainer = Box.createVerticalBox();
		initLayout(boxContainer);
	}
	
	private void initElements() {
		url = new JTextField(20);
		url.setText("http://www.qichacha.com/search_index?key=装饰材料&ajaxflag=1&province=ZJ&city=4");
		urlPages = new JTextField(20);
		urlPages.setText("1-50");
		filePath = new JTextField(20);
		filePath.setText("d:\\result\\");
		filePath.setEditable(false);
		fileName = new JTextField(20);
		fileName.setText("企查查.xlsx");

		button = new JButton("开始采集");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						button.setText("采集中...");
						button.paintImmediately(getBounds());
						button.setEnabled(false);
						String[] pageArray = (urlPages.getText()).trim().split("-");
						outPut.setText("开始采集数据：");
						outPut.paintImmediately(getBounds());
						startWork(Integer.valueOf(pageArray[0]), Integer.valueOf(pageArray[1]), url.getText(),
								filePath.getText(), fileName.getText());
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
				stop = true;
			}
		});
		
		outPut = new JTextArea(10, 30);
		outPut.setEditable(true);
		scroll = new JScrollPane(outPut);
	}
	
	private void initLayout(Box boxContainer) {
		Box labelBox, textBox, outPutBox, box, header, buttonBox;
		header = Box.createHorizontalBox();
		header.add(new JLabel("企查查采集"));
		box = Box.createHorizontalBox();
		labelBox = Box.createVerticalBox();
		labelBox.add(new JLabel("URL："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("页码区间："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("存档文件夹："));
		labelBox.add(Box.createVerticalStrut(25));
		labelBox.add(new JLabel("存档文件名："));
		textBox = Box.createVerticalBox();
		textBox.add(url);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(urlPages);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(filePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName);

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
		this.add(boxContainer);
	}
	
	public void startWork(int fromPage, int endPage, String url, String filePath, String fileName) {
		String[] columnNames = {"内容", "URL"};
		Excel excel = new Excel("D:\\result\\", "企查查数据（续存2017）.xlsx", "sheet1", columnNames);
		try {
			MyHttpClient myHttpClient = new MyHttpClient();//list-noimg,list-img
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
			for (int k = 0; k < industryCodes.length && !stop; k++) {
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
			for (int j = 1; j < 51 && !stop; j++) {
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
			for (int j = 1; j < 51 && !stop; j++) {
				String newUrl = url + status[1] + "&subindustrycode=52" + "&startDate=2017" + "&industrycode=F" + "&p=" + j;
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
}
