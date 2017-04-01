package com.gdnyt.dto;

import javax.swing.JTextField;

/**
 * 用于传递数据库连接信息的容器
 * @author zuo_ji
 *
 */
public class ConnectionInfoContainer {
	private JTextField host,port,username,password;

	
	
	public ConnectionInfoContainer(JTextField host, JTextField port,
			JTextField username, JTextField password) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public JTextField getHost() {
		return host;
	}

	public void setHost(JTextField host) {
		this.host = host;
	}

	public JTextField getPort() {
		return port;
	}

	public void setPort(JTextField port) {
		this.port = port;
	}

	public JTextField getUsername() {
		return username;
	}

	public void setUsername(JTextField username) {
		this.username = username;
	}

	public JTextField getPassword() {
		return password;
	}

	public void setPassword(JTextField password) {
		this.password = password;
	}
	
	

	
	
	
	
	
}
