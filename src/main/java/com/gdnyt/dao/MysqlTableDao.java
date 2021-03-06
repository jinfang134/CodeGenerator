package com.gdnyt.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gdnyt.model.Column;
import com.gdnyt.model.Table;

@Repository
public class MysqlTableDao implements TableDao {

	private static Logger log = Logger.getLogger(MysqlTableDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取数据库中的所有数据库名字
	 * 
	 * @return
	 */
	public String[] getSchemaList() {

		String sql = "select * from schemata ";
		List<String> schemaList = jdbcTemplate.query(sql, (rs, i) -> {
			return rs.getString("SCHEMA_NAME");
		});
		log.info(schemaList.toString());
		schemaList.remove("information_schema");
		schemaList.remove("mysql");
		schemaList.remove("performance_schema");
		return schemaList.toArray(new String[schemaList.size()]);

	}

	/**
	 * 获取数据库中某个库的所有数据表
	 * 
	 * @param dbName
	 * @return
	 */
	public List<String> getTableNames(String dbName) {
		String sql = "select * from tables where TABLE_SCHEMA= ?";
		List<String> tables = jdbcTemplate.query(sql, new Object[] { dbName }, (rs, index) -> {
			return rs.getString("TABLE_NAME");
		});
		log.info(tables.toString());
		return tables;
	}

	/**
	 * 获取表的各列
	 * 
	 * @param dbname
	 *            数据库名
	 * @param tablename
	 *            数据表名
	 * @return
	 * @throws SQLException
	 */
	public List<Column> getColumns(String dbname, String tablename) throws SQLException {
		String sql = "select * from columns where table_name=? and table_schema=?";
		return jdbcTemplate.query(sql, new Object[] { tablename, dbname }, (rs, i) -> {
			Column column = new Column();
			String comment = rs.getString("COLUMN_COMMENT").trim();
			// 如果备注为空，则用列名代替
			if (comment == null || comment.equals(""))
				comment = rs.getString("COLUMN_NAME").trim();
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
			return column;
		});

	}

	public Table getTable(String dbname, String tablename) {
		String sql = "select * from tables where table_name=? and table_schema=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { tablename, dbname }, (rs, i) -> {
			Table table = new Table();
			table.setComment(rs.getString("TABLE_COMMENT"));
			table.setName(tablename);
			table.setColumnlist(getColumns(dbname, tablename));
			return table;
		});
	}

	/**
	 * 进行数据库与java类型之间的映射
	 */
	public String getJavaType(String mysqlType) {
		String javaType = "String";
		switch (mysqlType.toLowerCase()) {
		case "bigint":
			javaType = "Long";
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
			javaType = "Date";
			break;
		case "tinyint":
			javaType = "Integer";
			break;
		case "smallint":
			javaType = "Integer";
			break;
		case "mediumint":
			javaType = "Integer";
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
