package com.gdnyt.model;

public class DBInfo {
	private String host, port, username, password;
	private String table;

	public DBInfo(String host, String port, String username, String password, String table) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.table = table;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
