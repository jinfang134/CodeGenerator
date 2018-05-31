package com.gdnyt.service;

import org.springframework.jdbc.core.JdbcTemplate;

import com.gdnyt.dao.TableDao;

public class DbresourceHolder {
	JdbcTemplate jdbcTemplate;
	TableDao tableDao;

	private DbresourceHolder() {
	};

	private static class Holder {
		private static DbresourceHolder holder = new DbresourceHolder();
	}

	public static DbresourceHolder getInstance() {
		return Holder.holder;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public TableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(TableDao tableDao) {
		this.tableDao = tableDao;
	}

}
