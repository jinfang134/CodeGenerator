package com.gdnyt.model;

import com.gdnyt.utils.ConfigUtils;

public class Setting {
	private static final String PREFIX = "prefix";
	public static String PROPERTIES="config.properties";
	public static String HOST="host";
	public static String PORT="port";
	public static String DBNAME="dbname";
	public static String USER="user";
	public static String PWD="pwd";
	public static String PATH="path";
	public static String PACKAGENAME="packagename";
	
	private String host;
	private String port;
	private String dbname;
	private String user;
	private String pwd;
	private String path;
	private String packagename;
	private String prefix;
	
	private static String viewsetname; //模块名
	
	private static Setting setting;
	private Setting(){};
	
	static{
		setting=new Setting();
	}
	
	public static Setting getInstance(){
		return setting;
	}
	
	
	public String getViewsetname() {
		return viewsetname;
	}


	public void setViewsetname(String viewsetname) {
		Setting.viewsetname = viewsetname;
	}


	public String getHost() {
		return ConfigUtils.get(PROPERTIES, HOST);
	}
	public void setHost(String host) {
		ConfigUtils.set(PROPERTIES, HOST,host);
	}
	public String getPort() {
		return ConfigUtils.get(PROPERTIES, PORT);
	}
	public void setPort(String port) {
		ConfigUtils.set(PROPERTIES, PORT, port);
	}
	public String getDbname() {
		return ConfigUtils.get(PROPERTIES, DBNAME);
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
		ConfigUtils.set(PROPERTIES, DBNAME, dbname);
	}
	public String getUser() {
		return ConfigUtils.get(PROPERTIES, USER);
	}
	public void setUser(String user) {
		ConfigUtils.set(PROPERTIES, USER, user);
	}
	public String getPwd() {
		return ConfigUtils.get(PROPERTIES, PWD);
	}
	public void setPwd(String pwd) {
		ConfigUtils.set(PROPERTIES, PWD, pwd);
	}

	public String getPath() {
		return ConfigUtils.get(PROPERTIES, PATH);
	}
	public void setPath(String path) {
		ConfigUtils.set(PROPERTIES, PATH,path);
	}

	public String getPackagename() {
		return ConfigUtils.get(PROPERTIES, PACKAGENAME);
	}
	public void setPackagename(String packagename) {
		ConfigUtils.set(PROPERTIES, PACKAGENAME,packagename);
	}

	public String getPrefix() {
		return ConfigUtils.get(PROPERTIES, PREFIX);
	}

	public void setPrefix(String prefix) {
		ConfigUtils.set(PROPERTIES, PREFIX,prefix);
	}
	
	
}
