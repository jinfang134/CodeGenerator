package com.gdnyt.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdnyt.model.Column;
import com.gdnyt.model.Table;

/**
 * 数据表操作接口
 * 
 * @author jinfang
 *
 */
@Repository
public interface TableDao {

	/**
	 * 获取数据库中的所有数据库名字
	 * 
	 * @return
	 */
	public String[] getSchemaList();

	/**
	 * 获取数据库中某个库的所有数据表
	 * 
	 * @param dbName
	 * @return
	 */
	public List<String> getTableNames(String dbName);

	/**
	 * 获取表的各列
	 * 
	 * @param dbname
	 *            数据库名
	 * @param tablename
	 * @return
	 * @throws SQLException
	 */
	List<Column> getColumns(String dbname, String tablename) throws SQLException;

	public Table getTable(String dbname, String tablename);

	/**
	 * 将数据表的字段转换为java的类型名
	 * 
	 * @param mysqlType
	 * @return
	 */
	public String getJavaType(String mysqlType);
}
