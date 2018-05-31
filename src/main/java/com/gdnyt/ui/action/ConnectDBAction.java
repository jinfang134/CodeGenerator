package com.gdnyt.ui.action;

import java.util.Observable;
import java.util.Observer;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gdnyt.dao.TableDao;
import com.gdnyt.dto.EventContent;
import com.gdnyt.dto.EventType;
import com.gdnyt.model.DBInfo;
import com.gdnyt.model.Setting;
import com.gdnyt.ui.component.Subject;

public class ConnectDBAction implements Observer {
	DBInfo dbInfo;
	Setting setting;
	JdbcTemplate jdbcTemplate;
	TableDao tableDao;

	public ConnectDBAction(TableDao tableDao, JdbcTemplate jdbcTemplate) {
		// putValue(NAME, "Bookmarks");
		this.jdbcTemplate = jdbcTemplate;
		this.tableDao = tableDao;
		setting = Setting.getInstance();
		Subject.getInstance().addObserver(this);
	}

	private DataSource getDataSource() {
		String url = "jdbc:mysql://%s:%s/information_schema?useUnicode=true&characterEncoding=UTF-8";
		PoolProperties properties = new PoolProperties();
		properties.setUrl(String.format(url, dbInfo.getHost(), dbInfo.getPort()));
		properties.setUsername(dbInfo.getUsername());
		properties.setPassword(dbInfo.getPassword());
		DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(properties);
		return dataSource;
	}

	private void saveConnectionInfo() {
		setting.setHost(dbInfo.getHost());
		setting.setPort(dbInfo.getPort());
		setting.setUser(dbInfo.getUsername());
		setting.setPwd(dbInfo.getPassword());
	}

	private void loadDataBase() {

		try {
			String[] schema = tableDao.getSchemaList();
			Subject.getInstance().notifyObservers(new EventContent(EventType.DB_CONNECTED, schema));
			Subject.getInstance().notifyObservers(new EventContent(EventType.SHOW_STATUS, "数据库连接成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			Subject.getInstance().notifyObservers(new EventContent(EventType.SHOW_STATUS, "连接出错，提示：" + e.getMessage()));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		EventContent eventContent = (EventContent) arg;
		if (eventContent.getEventType() == EventType.CONNECT_DB) {
			dbInfo = (DBInfo) eventContent.getData();
			jdbcTemplate.setDataSource(getDataSource());
			loadDataBase();
			saveConnectionInfo();
		}
	}
}
