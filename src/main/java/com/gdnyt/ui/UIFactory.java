package com.gdnyt.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gdnyt.dao.TableDao;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.service.DbresourceHolder;
import com.gdnyt.ui.panel.PanelGenFromTable;

@Component
public class UIFactory {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("jdbcTemplate2")
	private JdbcTemplate jdbcTemplate2;

	@Autowired
	private TableDao mysqlTableDao;

	@Autowired
	private CodeGenService codeGenService;

	public FrameMain createFrameMain() {
		DbresourceHolder.getInstance().setJdbcTemplate(jdbcTemplate2);
		DbresourceHolder.getInstance().setTableDao(mysqlTableDao);
		FrameMain window = new FrameMain(jdbcTemplate, jdbcTemplate2, mysqlTableDao, codeGenService);
		return window;
	}

	public PanelGenFromTable createPanelGenFromTable() {
		return new PanelGenFromTable(codeGenService);
	}
}
