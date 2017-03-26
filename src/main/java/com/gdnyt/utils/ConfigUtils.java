package com.gdnyt.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtils {
//	public static final String filename="config.properties";
	
	public static String get(String filename,String key) {
		File file=new File("./"+filename);
		if(!file.exists())
			return "";
		Properties p = null ;
		try {
			InputStream inputStream = new FileInputStream(file);			
			p = new Properties();
			p.load(inputStream);
		//	inputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p.getProperty(key);

	}
	
	public static String get(String key) {
		
		InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p.getProperty(key);

	}

	public static void set(String fileName,String key, String value) {	
		
        Properties props = null;  
        BufferedWriter bw = null;  
        InputStream stream = null;
        OutputStreamWriter writer=null;
        try {  
        	File file=new File("./"+fileName);
        	if(!file.exists()){
        		file.createNewFile();
        	}
        	props=new Properties();
        	stream=new FileInputStream(file);
        	props.load(stream);
            Map<String, String> map=new HashMap<String, String>();
            for(Object k:props.keySet()){
            	map.put((String)k, (String)props.get(k));
            }
            map.put(key, value);
            // 写入属性文件  
            writer=new OutputStreamWriter(new FileOutputStream(file));
            bw = new BufferedWriter(writer);                
            props.clear();// 清空旧的文件                
            for (String k : map.keySet())  
                props.setProperty(k, map.get(k));   
            props.store(bw, "");  
        } catch (IOException e) {  
           e.printStackTrace();
        } finally {  
            try {  
            	stream.close();
                bw.close();  
                writer.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  catch (Exception e2) {
				// TODO: handle exception
            	e2.printStackTrace();
			}
        } 
		
	}
        
//	public static void main(String[] a) {
////		ConfigUtils.updateProperties("a.properties",new HashMap<String, String>(){
////			{
////				put("hello", "world");
////			}
////		});
//		for(int i=0;i<10;i++)
//			ConfigUtils.set("a.properties",i+"", ""+1000+i);
//		
//		System.out.println(ConfigUtils.get("a.properties","9"));
//	}
}
