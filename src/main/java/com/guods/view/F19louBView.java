package com.guods.view;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.guods.contact.F19louBParser;
import com.guods.contact.MyHttpClient;
import com.guods.contact.Parser;

/**
 * 19楼采集页面B
 * @author guods
 *
 */
public class F19louBView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField preUrl, postUrl, filePath, fileName, urlPages;
	private JButton startButton, stopButton;
	private JTextArea outPut;
	private JScrollPane scroll;
	private volatile boolean stop = false;
	
	public F19louBView() throws HeadlessException {
		super("19楼数据采集B");
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
		preUrl = new JTextField(20);
		preUrl.setText("http://jinhua.19lou.com/r/61/zxrjbm-");
		postUrl = new JTextField(20);
		postUrl.setText(".html");
		urlPages = new JTextField(20);
		urlPages.setText("1-20");
		filePath = new JTextField(20);
		filePath.setText("d:\\result\\");
		filePath.setEditable(false);
		fileName = new JTextField(20);
		fileName.setText("19楼数据B.xlsx");

		startButton = new JButton("开始采集");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						startButton.setText("采集中...");
						startButton.paintImmediately(getBounds());
						startButton.setEnabled(false);
						String[] pageArray = (urlPages.getText()).trim().split("-");
						outPut.setText("开始采集数据：");
						outPut.paintImmediately(getBounds());
						startWork(Integer.valueOf(pageArray[0]), Integer.valueOf(pageArray[1]), preUrl.getText(),
								postUrl.getText(), filePath.getText(), fileName.getText());
						startButton.setText("重新采集");
						startButton.setEnabled(true);
						startButton.paintImmediately(getBounds());
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
		header.add(new JLabel("19楼采集1"));
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
		labelBox.add(new JLabel("存档文件名："));
		textBox = Box.createVerticalBox();
		textBox.add(preUrl);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(postUrl);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(urlPages);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(filePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName);

		buttonBox = Box.createHorizontalBox();
		buttonBox.add(startButton);
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
	
	@SuppressWarnings("unchecked")
	public void startWork(int fromPage, int endPage, String preUrl, String postUrl, String filePath, String fileName) {
		StringBuffer urlBuffer = new StringBuffer();
		MyHttpClient myHttpClient = new MyHttpClient();
		Document document;
		List<String[]> rowDataList;
		// 创建excel文档
		String[] columnNames = { "URL", "标题", "内容"};
		Excel excel = new Excel(filePath, fileName, "content", columnNames);
		// 创建一个解析器
		Parser parser = new F19louBParser();
		stop = false;
		// 修改页码拼接请求，一次循环处理一个页面
		for (int i = fromPage; i <= endPage && !stop; i++) {
			// 清空urlBuffer，重新组装url
			if (urlBuffer.capacity() > 0) {
				urlBuffer.delete(0, urlBuffer.capacity());
			}
			urlBuffer.append(preUrl).append(i).append(postUrl);
			// 访问url，获取document
			document = myHttpClient.get(urlBuffer.toString());
			// 解析document，并获得公司页面列表
			rowDataList = (List<String[]>) parser.parseList(document);
			for (String[] rowData : rowDataList) {
				if (stop) {
					break;
				}
				if (rowData[0] != null && rowData[0].trim() != "") {
					Document detDocument = myHttpClient.get(rowData[0]);
					parser.parseDet(detDocument, excel, rowData);
					outPut.append(System.lineSeparator() + "已采集" + excel.size() + "条");
					outPut.paintImmediately(getBounds());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			outPut.append(System.lineSeparator() + "第" + i + "页会员采集完成。。。");
			outPut.paintImmediately(getBounds());
		}
		stop = false;
		// 数据存档
		excel.saveFile();
		outPut.append(System.lineSeparator() + "采集完成，总共采到:" + excel.size() + "条！");
		outPut.paintImmediately(getBounds());
	}
}
