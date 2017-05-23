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
import com.guods.contact.GanjiParser;
import com.guods.contact.MyHttpClient;
import com.guods.contact.Parser;

/**
 * 赶集网采集页面
 * @author guods
 *
 */
public class GanjiView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField filePath, fileName, urlPages, url;
	private JButton startButton, stopButton;
	private JTextArea outPut;
	private JScrollPane scroll;
	private volatile boolean stop = false;
	
	public GanjiView() throws HeadlessException {
		super("数据采集神器");
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
		url = new JTextField();
		url.setText("http://hz.ganji.com/jiatingzhuangxiu/");
		urlPages = new JTextField(20);
		urlPages.setText("1-20");
		filePath = new JTextField(20);
		filePath.setText("d:\\result\\");
		filePath.setEditable(false);
		fileName = new JTextField(20);
		fileName.setText("赶集.xlsx");
		
		startButton = new JButton("开始采集");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					String urlText = url.getText() + "o";
					public void run() {
						startButton.setText("采集中...");
						startButton.paintImmediately(getBounds());
						startButton.setEnabled(false);
						outPut.setText("赶集网开始采集数据。。。");
						outPut.paintImmediately(getBounds());
						String[] pages = urlPages.getText().split("-");
						startWorkGanji(urlText, Integer.valueOf(pages[0]), Integer.valueOf(pages[1]), filePath.getText(), fileName.getText());
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
		
		outPut = new JTextArea(8, 30);
		outPut.setEditable(true);
		scroll = new JScrollPane(outPut);
	}
	
	private void initLayout(Box boxContainer) {
		Box labelBox, textBox, outPutBox, box, header, buttonBox, buttonBoxSub;
		header = Box.createHorizontalBox();
		header.add(new JLabel("赶集网采集"));
		box = Box.createHorizontalBox();
		labelBox = Box.createVerticalBox();
		labelBox.add(new JLabel("赶集类型："));
		labelBox.add(Box.createVerticalStrut(28));
		labelBox.add(new JLabel("采集页数："));
		labelBox.add(Box.createVerticalStrut(28));
		labelBox.add(new JLabel("存档文件夹："));
		labelBox.add(Box.createVerticalStrut(28));
		labelBox.add(new JLabel("存档文件名："));
		textBox = Box.createVerticalBox();
		textBox.add(url);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(urlPages);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(filePath);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(fileName);

		buttonBoxSub = Box.createVerticalBox();
		buttonBoxSub.add(startButton);
		
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
	
	public void startWorkGanji(String baseUrl, int fromPage, int endPage, String filePath, String fileName) {
		MyHttpClient myHttpClient = new MyHttpClient();
		Document document;
		// 创建excel文档
		String[] columnNames = { "标题", "客户名", "电话号码1", "电话号码2", "联系人", "URL"};
		Excel excel = new Excel(filePath, fileName, "ganji", columnNames);
		// 创建一个解析器
		Parser parser = new GanjiParser();
		stop = false;
		try {
			for (int i = fromPage; i <= endPage && !stop; i++) {
				System.out.println(baseUrl + i);
				document = myHttpClient.get(baseUrl + i);
				//解析列表
				List<String[]> secList = (List<String[]>) parser.parseList(document);
				//解析会员页面
				for (String[] item : secList) {
					document = myHttpClient.get(item[5]);
					parser.parseDet(document, excel, item);
				}
				outPut.append(System.lineSeparator() + "第:" + i + "页采集完成");
				outPut.paintImmediately(getBounds());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stop = false;
		// 数据存档
		excel.saveFile();
		outPut.append(System.lineSeparator() + "赶集网采集完成，总共:" + excel.size() + "条");
		outPut.paintImmediately(getBounds());
	}
}
