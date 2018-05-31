package com.gdnyt.dto;

public enum EventType {
	CONNECT_DB, DB_CONNECTED,
	/**
	 * 加载所有的数据库
	 */
	LOAD_DB,
	/**
	 * 加载某个数据库的所有表
	 */
	LOAD_TABLES,
	/**
	 * 选中表
	 */
	TABLE_SELECTED, SHOW_STATUS;
}
