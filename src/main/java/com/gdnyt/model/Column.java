package com.gdnyt.model;

import com.gdnyt.utils.StringUtils;

/**
 * 表中的列
 * 
 * @author jinfang
 */
public class Column {
	private String name;
	private String comment;
	private String type;
	private int length;
	private String bigname;
	private String columnname;
	private String isnull;
	private String columntype;
	private String columnkey;
	private String extra;

	@Override
	public String toString() {
		return "Column [name=" + name + ", comment=" + comment + ", type=" + type + ", length=" + length + ", bigname="
				+ bigname + "]\n";
	}

	public String getIsnull() {
		return isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getColumntype() {
		return columntype;
	}

	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}

	public String getColumnkey() {
		return columnkey;
	}

	public void setColumnkey(String columnkey) {
		this.columnkey = columnkey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.contains("_")) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String s : name.split("_")) {
				stringBuilder.append(StringUtils.firstToUpper(s));
			}
			this.bigname = stringBuilder.toString();
			this.name = StringUtils.firstToLower(stringBuilder.toString());
		} else {
			this.name = StringUtils.firstToLower(name);
			this.bigname = StringUtils.firstToUpper(name);
		}
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnName) {
		this.columnname = columnName;
	}

	public String getBigname() {
		return bigname;
	}

	public void setBigname(String bigname) {
		this.bigname = StringUtils.firstToUpper(bigname);
	}

}
