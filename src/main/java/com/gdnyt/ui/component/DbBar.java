package com.gdnyt.ui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.sql.DataSource;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.gdnyt.dto.EventContent;
import com.gdnyt.dto.EventType;
import com.gdnyt.dto.SchemaInfo;
import com.gdnyt.model.DBInfo;
import com.gdnyt.model.Setting;
import com.gdnyt.service.DbresourceHolder;
import com.gdnyt.ui.FrameMain;

public class DbBar extends JToolBar implements Observer {

	private JTextField host_text;
	private JTextField port_text;
	private JTextField user_text;
	private JPasswordField pwd_text;
	private JComboBox db_comboBox;
	private JButton connectButton;
	private JButton initbutton;

	private Setting setting;

	public void initView() {
		JToolBar toolBar = this;
		JLabel label = new JLabel(" 数据库地址：");
		toolBar.add(label);

		host_text = new JTextField();
		toolBar.add(host_text);
		host_text.setColumns(7);

		JLabel lblNewLabel_5 = new JLabel(" 端口：");
		toolBar.add(lblNewLabel_5);

		port_text = new JTextField();
		toolBar.add(port_text);
		port_text.setColumns(5);

		JLabel lblNewLabel = new JLabel(" 账号：");
		toolBar.add(lblNewLabel);

		user_text = new JTextField();
		toolBar.add(user_text);
		user_text.setColumns(6);

		JLabel lblNewLabel_1 = new JLabel(" 密码：");
		toolBar.add(lblNewLabel_1);

		pwd_text = new JPasswordField();
		pwd_text.setColumns(6);
		toolBar.add(pwd_text);

		db_comboBox = new JComboBox();
		connectButton = new JButton("连接数据库");
		connectButton.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/database_lightning.png")));
		toolBar.add(connectButton);
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBInfo dbInfo = new DBInfo(host_text.getText(), port_text.getText(), user_text.getText(),
						pwd_text.getText(), "");
				EventContent eventContent = new EventContent(EventType.CONNECT_DB, dbInfo);
				// Subject.getInstance().hasChanged();
				Subject.getInstance().notifyObservers(eventContent);
				System.out.println("连接数据库,notify:" + Subject.getInstance().countObservers());
			}
		});

		JLabel lblNewLabel_2 = new JLabel(" 数据库：");
		toolBar.add(lblNewLabel_2);
		toolBar.add(db_comboBox);

		// 添加初始化按钮
		initbutton = new JButton(" 加载表");
		initbutton.setIcon(new ImageIcon(FrameMain.class.getResource("/icon/refresh.png")));
		initbutton.addActionListener(new LoadDBAction());
		toolBar.add(initbutton);
	}

	private void loadSetting() {
		setting = Setting.getInstance();
		host_text.setText(setting.getHost());
		port_text.setText(setting.getPort());
		user_text.setText(setting.getUser());
		pwd_text.setText(setting.getPwd());
	}

	public DbBar() {
		Subject.getInstance().addObserver(this);
		initView();
		loadSetting();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		EventContent eventContent = (EventContent) arg;
		if (eventContent.getEventType() == EventType.DB_CONNECTED) {
			String[] schema = (String[]) eventContent.getData();
			db_comboBox.setModel(new DefaultComboBoxModel<>(schema));
		}
	}

	private class LoadDBAction extends AbstractAction {
		private String dbName;

		public LoadDBAction() {
			putValue(NAME, "Bookmarks");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dbName = (String) db_comboBox.getSelectedItem();
			setting.setDbname(dbName);
			DbresourceHolder.getInstance().getJdbcTemplate().setDataSource(getDataSource());
			List<String> list = DbresourceHolder.getInstance().getTableDao().getTableNames(dbName);
			SchemaInfo schemaInfo = new SchemaInfo(dbName, list);
			Subject.getInstance().notifyObservers(new EventContent(EventType.LOAD_TABLES, schemaInfo));
			Subject.getInstance()
					.notifyObservers(new EventContent(EventType.SHOW_STATUS, "从数据库中读取出" + list.size() + "个表。"));
		}

		private DataSource getDataSource() {
			String url = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8";
			PoolProperties properties = new PoolProperties();
			properties.setUrl(String.format(url, host_text.getText(), port_text.getText(), dbName));
			properties.setUsername(user_text.getText());
			properties.setPassword(pwd_text.getText());
			DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(properties);
			return dataSource;
		}

		/**
		 * 加载树
		 */

	}

}
