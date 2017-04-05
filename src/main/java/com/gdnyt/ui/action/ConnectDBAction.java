package com.gdnyt.ui.action;

import java.awt.event.ActionEvent;

import javax.sql.DataSource;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gdnyt.dao.TableDao;
import com.gdnyt.dto.ConnectionInfoContainer;
import com.gdnyt.model.Setting;

public class ConnectDBAction extends AbstractAction {
	ConnectionInfoContainer connectionInfo;
	Setting setting;
	JdbcTemplate jdbcTemplate;
	TableDao tableDao;
	JComboBox db_comboBox;
	
	public ConnectDBAction(TableDao tableDao,ConnectionInfoContainer connectionInfo,
			JdbcTemplate jdbcTemplate,JComboBox db_comboBox) {
		//putValue(NAME, "Bookmarks");
		this.jdbcTemplate=jdbcTemplate;
		this.connectionInfo=connectionInfo;
		this.db_comboBox=db_comboBox;
		this.tableDao=tableDao;
		setting=Setting.getInstance();
	}
	
	private DataSource getDataSource(){
		String url ="jdbc:mysql://%s:%s/information_schema?useUnicode=true&characterEncoding=UTF-8";
		PoolProperties properties=new PoolProperties();
		properties.setUrl(String.format(url, connectionInfo.getHost().getText(),connectionInfo.getPort().getText()));
		properties.setUsername(connectionInfo.getUsername().getText());
		properties.setPassword(connectionInfo.getPassword().getText());
		DataSource dataSource=new org.apache.tomcat.jdbc.pool.DataSource(properties);
		return dataSource;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jdbcTemplate.setDataSource(getDataSource());
		loadDataBase();
		saveConnectionInfo();
	}

	private void saveConnectionInfo() {
		setting.setHost(connectionInfo.getHost().getText());
		setting.setPort(connectionInfo.getPort().getText());
		setting.setUser(connectionInfo.getUsername().getText());
		setting.setPwd(connectionInfo.getPassword().getText());
	}
	
	
	
	private void loadDataBase() {

		try {
			String[] schema = tableDao.getSchemaList();
			db_comboBox.setModel(new DefaultComboBoxModel<>(schema));
			db_comboBox.setSelectedItem(setting.getDbname());
			JOptionPane.showMessageDialog(null, "数据库连接成功", "恭喜您", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据库连接失败", "连接出错，提示："+e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}
}
