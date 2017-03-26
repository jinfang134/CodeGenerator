package com.gdnyt.utils;

import javax.swing.JOptionPane;

public class MessageBox {
	
	private MessageBox(){};

	public static void showMessage(String title,String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showInfoMessage(String message){
		JOptionPane.showMessageDialog(null, message, "提示", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message, "错误", JOptionPane.ERROR_MESSAGE);
	}
}
