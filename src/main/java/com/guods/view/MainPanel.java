package com.guods.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 主页面
 * @author guods
 *
 */
public class MainPanel extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainPanel() {
		super("采集神器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setResizable(false);
		setVisible(true);
		initLayout();
	}

	private void initLayout(){
		setLayout(new FlowLayout());
		Box box;
		box = Box.createVerticalBox();
		addButton(box, "58同城采集", F58View.class);
		box.add(Box.createVerticalStrut(10));
		addButton(box, "今日头条采集", ToutiaoView.class);
		box.add(Box.createVerticalStrut(10));
		addButton(box, "赶集网采集", GanjiView.class);
		box.add(Box.createVerticalStrut(10));
		addButton(box, "19楼数据采集A", F19louAView.class);
		box.add(Box.createVerticalStrut(10));
		addButton(box, "19楼数据采集B", F19louBView.class);
		box.add(Box.createVerticalStrut(10));
		addButton(box, "企查查数据采集", QichachaView.class);
		add(box);
	}
	
	/**
	 * 向container添加一个按钮
	 * @param box 放button的容器
	 * @param button
	 * @param clazz 跳转的类
	 */
	private void addButton(Box box, String buttonName,final Class<? extends JFrame> clazz){
		JButton button = new JButton(buttonName);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						try {
							clazz.newInstance();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		box.add(button);
	}
}
