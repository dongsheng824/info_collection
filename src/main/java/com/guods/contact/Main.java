package com.guods.contact;

import java.util.List;

import javax.swing.SwingUtilities;

import com.guods.view.MainView;

public class Main 
{
    public static void main( String[] args )
    {
    	SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainView();
			}
		});
//    	startWork(1, 2);
    }
    
}
