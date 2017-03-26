package com.gdnyt.model;

import java.util.List;

import com.gdnyt.utils.StringUtils;

public class Table {

	private String packagename;
	private String name;
	private List<Column> columnlist;
	private int colsize;
	private String comment;
	private String classname;
	/**
	 * 去掉前缀的表名
	 * 
	 */
	private String lowername;

	private String prefix;
	private Setting setting;

	public Table() {
		setting = Setting.getInstance();
		prefix = setting.getPrefix();
		packagename = setting.getPackagename();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setLowername(name);
		setClassname(name);
	}

	public List<Column> getColumnlist() {
		return columnlist;
	}

	public void setColumnlist(List<Column> columnlist) {
		this.columnlist = columnlist;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getColsize() {
		return columnlist.size();
	}

	public void setColsize(int colsize) {
		this.colsize = columnlist.size();
	}

	@Override
	public String toString() {
		return "Table [name=" + name + ", columnlist=" + columnlist + ", comment=" + comment + ", classname="
				+ classname + "]\n";
	}

	public String getClassname() {
		return classname;
	}
	/**
	 * 
	 * @param tableName
	 */
	public void setClassname(String tableName) {
		String[] pre = prefix.split(",");
		for (String s : pre) {
			 //替换表名前缀。
			tableName=tableName.replaceFirst(s, "");
		}
		if (tableName.contains("_")) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String s : tableName.split("_")) {
				stringBuilder.append(StringUtils.firstToUpper(s));
			}
			this.classname = stringBuilder.toString();
		} else {
			this.classname = StringUtils.firstToUpper(tableName);
		}
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public String getLowername() {
		return lowername;
	}

	/**
	 * 去掉表名前缀
	 * 
	 * @param tableName
	 */
	public void setLowername(String tableName) {

		String[] pre = prefix.split(",");
		for (String s : pre) {
			tableName = tableName.replace(s, "");
		}
		if (tableName.contains("_")) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String s : tableName.split("_")) {
				stringBuilder.append(StringUtils.firstToUpper(s));
			}
			this.lowername = StringUtils.firstToLower(stringBuilder.toString());
		} else {
			this.lowername = StringUtils.firstToLower(tableName);
		}
	}

}
