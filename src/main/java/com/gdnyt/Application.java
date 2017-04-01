package com.gdnyt;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gdnyt.dao.MysqlTableDao;
import com.gdnyt.dao.TableDao;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.ui.FrameMain;
@ComponentScan
@SpringBootApplication
public class Application implements ApplicationRunner{

	public static void main(String[] args) {
		ApplicationContext context =  new SpringApplicationBuilder(Application.class).headless(false).run(args);
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TableDao mysqlTableDao;
	
	@Autowired
	private CodeGenService codeGenService;
	
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					FrameMain window=new FrameMain(jdbcTemplate, mysqlTableDao, codeGenService);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
}
