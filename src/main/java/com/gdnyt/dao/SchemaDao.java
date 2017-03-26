package com.gdnyt.dao;

/**
 * 数据库操作接口
 * @author jinfang
 *
 */
public interface SchemaDao {
	
	/**
	 * 获取数据库中的所有数据库名字
	 * @return
	 */
	public String[] getSchemaList();
	
	/**
	 * 获取数据库中某个库的所有数据表
	 * @param dbName
	 * @return
	 */
	public String[] getTableNames(String dbName);
}
