package com.guods.view;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import com.guods.contact.JsoupHttpClient;
import com.guods.contact.PageParser;
import com.guods.toutiao.model.AsCp;

/**
 * 今日头条采集页面
 * @author guods
 *
 */
public class ToutiaoView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField collectionCount, filePath, fileName, commentsCount, keyword;
	private JButton startButton, stopButton, searchButton;
	private JTextArea outPut;
	private JScrollPane scroll;
	private JComboBox type;
	private volatile boolean stop = false;
	private String cookies = "uuid=w:d7dcefd0fde74bc8a90c0667364c5f30;UM_distinctid=15b23c7417a1ed-07781e3212b944-6a11157a-1fa400-15b23c7417b22b;utm_source=toutiao;tt_webid=57234957546;_ga=GA1.2.1673071305.1490953913";
	
	public ToutiaoView() throws HeadlessException {
		super("今日头条数据采集");
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
	
	@SuppressWarnings("unchecked")
	private void initElements() {
		type = new JComboBox();
		type.addItem("首页");
		type.addItem("家居");
		type.addItem("社会");
		type.addItem("搞笑");
		type.addItem("热点");
		keyword = new JTextField(20);
		keyword.setText("装修");
		collectionCount = new JTextField(20);
		collectionCount.setText("20000");
		filePath = new JTextField(20);
		filePath.setText("d:\\result\\");
		filePath.setEditable(false);
		fileName = new JTextField(20);
		fileName.setText("今日头条.xlsx");
		commentsCount = new JTextField(20);
		commentsCount.setText("50");
		
		startButton = new JButton("开始采集");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					String url = "http://www.toutiao.com/api/pc/feed/?category=__all__&utm_source=toutiao&widen=1&max_behot_time=1491359690&max_behot_time_tmp=1491359690&tadrequire=false&as=A195286EB455C9D&cp=58E4356CA92D7E1";
					public void run() {
						if (type.getSelectedItem().toString().equals("首页")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=__all__&utm_source=toutiao&widen=1&max_behot_time=1491359690&max_behot_time_tmp=1491359690&tadrequire=false&as=A195286EB455C9D&cp=58E4356CA92D7E1";
						};
						if (type.getSelectedItem().toString().equals("家居")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=news_home&utm_source=toutiao&widen=1&max_behot_time=1491352754&max_behot_time_tmp=1491352754&tadrequire=true&as=A155286E14A5718&cp=58E4F5674108DE1";
						};
//						if (toutiaoType.getSelectedItem().toString().equals("段子")) {
//							url = "http://www.toutiao.com/api/article/feed/?category=essay_joke&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A10548FE0485C41&cp=58E4855C44F10E1";
//						};
						if (type.getSelectedItem().toString().equals("搞笑")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=funny&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A165D8CEB4D5C6F&cp=58E495BC567F4E1";
						};
						if (type.getSelectedItem().toString().equals("社会")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=news_society&utm_source=toutiao&widen=1&max_behot_time=1491357717&max_behot_time_tmp=1491357717&tadrequire=true&as=A1A5C87ED4B5E29&cp=58E4954EE2397E1";
						};
						if (type.getSelectedItem().toString().equals("热点")) {
							url = "http://www.toutiao.com/api/pc/feed/?category=news_hot&utm_source=toutiao&widen=1&max_behot_time=1491360111&max_behot_time_tmp=1491360111&tadrequire=true&as=A1D5D84E4415E81&cp=58E4C5FEE8715E1";
						};
						startButton.setText("采集中...");
						startButton.paintImmediately(getBounds());
						startButton.setEnabled(false);
						outPut.setText("今日头条开始采集数据。。。");
						outPut.paintImmediately(getBounds());
						startWorkToutiao(Integer.valueOf(collectionCount.getText()), 
								url, 
								filePath.getText(), fileName.getText(), Integer.valueOf(commentsCount.getText()),
								keyword.getText());
						startButton.setText("重新采集");
						startButton.setEnabled(true);
						startButton.paintImmediately(getBounds());
					}
				}).start();
			}
		});
		
		searchButton = new JButton("开始搜索");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					String preUrl = "http://www.toutiao.com/search_content/?format=json&autoload=true&count=20&cur_tab=1";
					public void run() {
						searchButton.setText("采集中...");
						searchButton.paintImmediately(getBounds());
						searchButton.setEnabled(false);
						outPut.setText("今日头条开始搜索数据。。。");
						outPut.paintImmediately(getBounds());
						startSearchToutiao(preUrl, Integer.valueOf(collectionCount.getText()),
								filePath.getText(), fileName.getText(), 
								Integer.valueOf(commentsCount.getText()),
								keyword.getText());
						searchButton.setText("重新搜索");
						searchButton.setEnabled(true);
						searchButton.paintImmediately(getBounds());
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
		textBox.add(type);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(keyword);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(collectionCount);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(commentsCount);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(filePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName);

		buttonBoxSub = Box.createVerticalBox();
		buttonBoxSub.add(startButton);
		buttonBoxSub.add(Box.createVerticalStrut(8));
		buttonBoxSub.add(searchButton);
		
		buttonBox = Box.createHorizontalBox();
		buttonBox.add(buttonBoxSub);
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
		//
		boxContainer.add(header);
		boxContainer.add(box);
		this.add(boxContainer);
	}
	
	public void startWorkToutiao(int count, String url, String filePath, String fileName, int toutiaoCommCount, String keyword) {
		StringBuffer urlBuffer = new StringBuffer();
		JsoupHttpClient myHttpClient = new JsoupHttpClient();
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
		stop = false;
		try {
			while (!stop && excel.size() < count) {
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
				document = myHttpClient.cookieGet(urlBuffer.toString(), cookies);
				tag = parser.parseToutiao(document, excel, toutiaoCommCount, false, keyword);
				if (tag) {
					outPut.append(System.lineSeparator() + "已采到:" + (excel.size() - 1) + "条");
					outPut.paintImmediately(getBounds());
				}
				maxBehotTime = maxBehotTime - 1000;
				i++;
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stop = false;
		// 数据存档
		excel.saveFile();
		outPut.append(System.lineSeparator() + "头条采集完成，总共:" + excel.size() + "条");
		outPut.paintImmediately(getBounds());
	}
	
	public void startSearchToutiao(String preUrl, int count, String filePath, String fileName, int toutiaoCommCount, String keyword) {
		StringBuffer urlBuffer = new StringBuffer();
		JsoupHttpClient myHttpClient = new JsoupHttpClient();
		Document document;
		boolean hasMore = true;
		// 创建excel文档
		String[] columnNames = { "标题", "URL", "评论数量" };
		Excel excel = new Excel(filePath, fileName, "contact", columnNames);
		// 创建一个解析器
		PageParser parser = new PageParser();
		int i = 0;
		stop = false;
		try {
			while (!stop && excel.size() < count && hasMore) {
				System.out.println(i);
				// 清空urlBuffer，重新组装url
				if (urlBuffer.capacity() > 0) {
					urlBuffer.delete(0, urlBuffer.capacity());
				}
				urlBuffer.append(preUrl).append("&offset=").append(i * 20)
					.append("&keyword=").append(keyword);
				System.out.println(urlBuffer.toString());
				document = myHttpClient.cookieGet(urlBuffer.toString(), cookies);
				hasMore = parser.parseToutiao(document, excel, toutiaoCommCount, true, keyword);
				if (hasMore) {
					outPut.append(System.lineSeparator() + "已采到:" + (excel.size() - 1) + "条");
					outPut.paintImmediately(getBounds());
				}
				i++;
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stop = false;
		// 数据存档
		excel.saveFile();
		outPut.append(System.lineSeparator() + "头条采集完成，总共:" + excel.size() + "条");
		outPut.paintImmediately(getBounds());
	}
}
