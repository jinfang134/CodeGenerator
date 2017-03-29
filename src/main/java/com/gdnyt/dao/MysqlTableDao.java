package com.gdnyt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gdnyt.model.Column;
import com.gdnyt.model.Table;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MysqlTableDao implements TableDao{
	
	private static Logger log = Logger.getLogger(MysqlTableDao.class);

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
			e.printStackTrace();
			log.error(e);
		}
		log.debug(schemaList.toString());	
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
			e.printStackTrace();
			log.error(e);
		}
		log.info(tables.toString());		
		return tables.toArray(new String[tables.size()]);
	}
	
	/**
	 * 获取表的各列
	 * 
	 * @param dbname 数据库名
	 * @param tablename 数据表名
	 * @return
	 * @throws SQLException
	 */
	public List<Column> getColumns(String dbname,String tablename) throws SQLException {
		Connection con = JDBCUtil.getConnection();
		Statement stmt = (Statement) con.createStatement();
		String query = "select * from columns where table_name='" + tablename + "' and table_schema='"+dbname+"'";
		ResultSet rs = stmt.executeQuery(query);
		List<Column> columns = new ArrayList<>();
		while (rs.next()) {
			Column column = new Column();
			String comment=rs.getString("COLUMN_COMMENT").trim();
			//如果备注为空，则用列名代替
			if(comment==null||comment.equals(""))
				comment=rs.getString("COLUMN_NAME").trim();
			column.setComment(comment);
			column.setName(rs.getString("COLUMN_NAME").trim());
			column.setColumnname(rs.getString("COLUMN_NAME").trim());
			column.setLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
			column.setType(getJavaType(rs.getString("DATA_TYPE")).trim());
			column.setIsnull(rs.getString("IS_NULLABLE"));
			column.setColumntype(rs.getString("COLUMN_TYPE"));
			column.setColumnkey(rs.getString("COLUMN_KEY"));
			column.setExtra(rs.getString("EXTRA"));		
			column.getBigname();
			columns.add(column);
		}
		JDBCUtil.closeAll(rs, stmt, con);
		log.debug(columns.toString());
		return columns;
	}

	public Table getTable(String dbname,String tablename) {
		Table table = null;
		Connection con = JDBCUtil.getConnection();
		try {
			Statement stmt = (Statement) con.createStatement();
			String query = "select * from tables where table_name='" + tablename + "' and table_schema='"+dbname+"'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				table = new Table();
				table.setComment(rs.getString("TABLE_COMMENT"));
				table.setName(tablename);
				table.setColumnlist(getColumns(dbname,tablename));				
			}
			JDBCUtil.closeAll(rs, stmt, con);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e);
		}
		log.debug(table.toString());
		return table;
	}
	

	/**
	 * 进行数据库与java类型之间的映射
	 */
	public String getJavaType(String mysqlType) {
		String javaType = "String";
		switch (mysqlType.toLowerCase()) {
		case "bigint":
			javaType="Long";
			break;
		case "varchar":
			javaType = "String";
			break;
		case "date":
			javaType = "LocalDate";
			break;
		case "datetime":
			javaType = "LocalDateTime";
			break;
		case "time":
			javaType = "LocalTime";
			break;
		case "int":
			javaType = "Integer";
			break;
		case "timestamp":
			javaType="Date";
			break;
		case "tinyint":
			javaType = "Integer";
			break;
		case "smallint":
			javaType = "Integer";
			break;
		case "mediumint":
			javaType="Integer";
			break;
		case "bit":
			javaType = "Boolean";
			break;
		case "float":
			javaType = "Float";
			break;
		case "double":
			javaType = "Double";
			break;
		case "decimal":
			javaType = "BigDecimal";
			break;
		}
		return javaType;
	}

}
