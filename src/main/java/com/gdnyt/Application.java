package com.gdnyt;

import java.awt.EventQueue;

import javax.sql.DataSource;
import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.session.SessionProperties.Jdbc;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gdnyt.dao.TableDao;
import com.gdnyt.service.CodeGenService;
import com.gdnyt.ui.FrameMain;
import com.gdnyt.ui.UIFactory;

@ComponentScan
@SpringBootApplication
public class Application implements ApplicationRunner {
	
	@Autowired
	UIFactory uiFactory;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					FrameMain window = uiFactory.createFrameMain();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(Application.class).headless(false).run(args);
	}

}
