package com.gdnyt.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gdnyt.model.Setting;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class JDBCUtil {
	public static Setting setting= Setting.getInstance();
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url ="jdbc:mysql://"+setting.getHost()+":"+setting.getPort()+"/information_schema?useUnicode=true&characterEncoding=UTF-8";
		String user=setting.getUser();	
		String pwd=setting.getPwd();
		
		try {
			conn = (Connection) DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeAll(ResultSet rs, Statement stat, Connection conn) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (stat != null)
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
