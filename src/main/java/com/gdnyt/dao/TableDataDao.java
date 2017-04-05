package com.gdnyt.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TableDataDao {
	private static Logger log = Logger.getLogger(MysqlTableDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate2;
	
	/**
	 * 获取表的原始数据
	 * @param table 
	 * @return
	 */
	public List<Map<String, Object>> queryAll(String table){
		//TODO 获取表的数据。
		String sql="select * from "+table;
		return jdbcTemplate2.queryForList(sql);
	}
	
}
