package com.gdnyt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * mysql 的数据库操作类
 * @author jinfang
 *
 */
public class MysqlSchemaDao implements SchemaDao {	
	private static Logger log = Logger.getLogger(MysqlSchemaDao.class);

	/**
	 * 获取数据库中的所有数据库名字
	 * @return
	 */
	public String[] getSchemaList(){
		List<String> schemaList=new ArrayList<>();
		Connection con = JDBCUtil.getConnection();
		try {
			Statement stmt = (Statement) con.createStatement();
			String query = "select * from schemata ";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {				
				schemaList.add(rs.getString("SCHEMA_NAME"));				
			}
			JDBCUtil.closeAll(rs, stmt, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e);
		}
		log.info(schemaList.toString());	
		schemaList.remove("information_schema");
		schemaList.remove("mysql");
		schemaList.remove("performance_schema");
		return schemaList.toArray(new String[schemaList.size()]);
	}
	
	/**
	 * 获取数据库中某个库的所有数据表
	 * @param dbName
	 * @return
	 */
	public String[] getTableNames(String dbName){
		List<String> tables=new ArrayList<>();
		Connection con = JDBCUtil.getConnection();
		try {
			Statement stmt = (Statement) con.createStatement();
			String query = "select * from tables where TABLE_SCHEMA= '"+dbName+"'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {				
				tables.add(rs.getString("TABLE_NAME"));				
			}
			JDBCUtil.closeAll(rs, stmt, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e);
		}
		log.info(tables.toString());		
		return tables.toArray(new String[tables.size()]);
	}


}
