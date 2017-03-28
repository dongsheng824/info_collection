package com.guods.contact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel
 * 
 * @author guods
 * 扩展：excel读写数据可以模仿集合实现增删改查等方法，方便调用
 * 如，把本类实现Iterable接口，再写个实现Iterator的内部类，就可以和集合一样迭代excel每一行的数据，这里用不到暂且不写
 */
public class Excel {

	private String filePath;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFCellStyle titleStyle, commonStyle;
	private int rowCount;	//总记录数
	
	/**
	 * 
	 * @param file 文件名
	 * @param sheetName sheet名
	 * @param columnNames 第一行内容（标题）
	 */
	public Excel(String file, String sheetName, String[] columnNames) {
		super();
		this.filePath = file;
		newSheet(sheetName, columnNames);
	}
	/**
	 * 初始化excel表格，生成标题（第一行数据）
	 * @param sheetName	sheet标签名
	 * @param columnNames 列名
	 */
	private void newSheet(String sheetName, String[] columnNames){
		workbook = new XSSFWorkbook();
		//设置标题字体
		XSSFFont titleFont = workbook.createFont();
		titleFont.setBold(true);
		titleFont.setFontName("黑体");
		titleStyle = workbook.createCellStyle();
		titleStyle.setFont(titleFont);
		//设置正文字体
		XSSFFont commonFont = workbook.createFont();
		commonFont.setBold(false);
		commonFont.setFontName("宋体");
		commonStyle = workbook.createCellStyle();
		commonStyle.setFont(commonFont);
		//创建sheet
		sheet = workbook.createSheet(sheetName);
		//设置初始记录行数
		rowCount = 0;
		//创建标题行
		insertRow(columnNames, titleStyle);
	}
	public void insertRow(String[] rowData){
		insertRow(rowData, commonStyle);
	}
	/**
	 * 插入一行数据
	 * @param rowData 数据内容
	 * @param style 字体风格
	 */
	public void insertRow(String[] rowData, XSSFCellStyle style){
		XSSFRow row = sheet.createRow(rowCount);
		for (int i = 0; i < rowData.length; i++) {
			XSSFCell cell = row.createCell(i);
			sheet.setColumnWidth(i, 5000);
			cell.setCellStyle(style);
			cell.setCellValue(rowData[i]);
		}
		rowCount++;
	}
	/**
	 * excel文件存储到磁盘
	 */
	public void saveFile(){
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			workbook.write(fileOutputStream);
		} catch (FileNotFoundException e) {
			System.out.println(filePath + "保存文件错误:" + e.getMessage());
		} catch (IOException e) {
			System.out.println(filePath + "保存文件错误:" + e.getMessage());
		}
	}
	/**
	 * 返回excel总行数
	 * @return
	 */
	public int size() {
		return rowCount;
	}
	
}
