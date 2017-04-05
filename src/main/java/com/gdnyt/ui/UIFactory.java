package com.gdnyt.ui;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gdnyt.dao.TableDao;
import com.gdnyt.service.CodeGenService;

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
	
	public FrameMain createFrameMain(){
		FrameMain window = new FrameMain(jdbcTemplate,jdbcTemplate2, mysqlTableDao, codeGenService);
		return window;
	}
	
	public PanelGenFromTable createPanelGenFromTable(){
		return new PanelGenFromTable(codeGenService);
	}
}
