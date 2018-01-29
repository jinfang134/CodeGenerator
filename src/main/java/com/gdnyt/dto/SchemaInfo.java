package com.gdnyt.dto;

import java.util.List;

public class SchemaInfo {
	private String dbName;
	private List<String> tableNames;

	public SchemaInfo(String dbName, List<String> tableNames) {
		super();
		this.dbName = dbName;
		this.tableNames = tableNames;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

}
