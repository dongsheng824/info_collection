package com.guods.leleketang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

	public static final String PAPERS_FOLDER = "/papers/";
	public static final String IMAGES_FOLDER = "/images/";

	private String baseDir;
	private String paperDir;
	private String imageDir;

	public FileManager(String baseFilePath) {
		super();
		this.baseDir = baseFilePath;
		this.paperDir = baseFilePath + PAPERS_FOLDER;
		this.imageDir = baseFilePath + IMAGES_FOLDER;
		init();
	}

	public void init() {
		createFolder(paperDir);
		createFolder(imageDir);
	}

	private synchronized void createFolder(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public synchronized String getPaperFile(String fileName) {
		String paperFile = paperDir + fileName;
		File file = new File(paperFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return paperFile;
	}

	public synchronized void appendContent(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件     
            writer = new FileWriter(fileName, true);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    } 
	
	public synchronized File createImageFile(String fileName){
		File file = new File(imageDir + fileName);
		return file;
	}
	
	public String getBaseDir() {
		return baseDir;
	}

	public String getPaperDir() {
		return paperDir;
	}

	public String getImageDir() {
		return imageDir;
	}

}
