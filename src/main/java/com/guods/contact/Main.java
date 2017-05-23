package com.guods.contact;

import javax.swing.SwingUtilities;
import com.guods.view.MainPanel;

public class Main 
{
    public static void main( String[] args )
    {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainPanel();
			}
		});
    	
    	//////////////////////////////////
    	//企查查采集
//    	Qichacha qichacha = new Qichacha();
//    	try {
//			qichacha.getRelContent("D:\\result\\", "qichacha.xlsx", "qichachaResult.xlsx");
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }
    
}
