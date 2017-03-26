package com.gdnyt.utils;

public class StringUtils {
	
	private StringUtils(){};
	
	
	public static String firstToUpper(String string){
		return string.substring(0,1).toUpperCase()+string.substring(1);
	}
	
	public static String firstToLower(String string){
		return string.substring(0,1).toLowerCase()+string.substring(1);
	}
}
