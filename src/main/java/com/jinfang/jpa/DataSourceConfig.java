package com.jinfang.jpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

//@Configuration
public class DataSourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
	static final String PACKAGE = "org.spring.springboot.dao.master";
	static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

	@Value("${spring.second-datasource.url}")
	private String url;

	@Value("${spring.second-datasource.username}")
	private String user;

	@Value("${spring.second-datasource.password}")
	private String password;

	@Value("${spring.second-datasource.driver-class-name}")
	private String driverClass;

	@Bean(name = "dataSource")
	@Primary
	public DataSource masterDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setValidationQueryTimeout(60000);
		return dataSource;
	}

	@Bean(name = "transactionManager")
	@Primary
	public DataSourceTransactionManager masterTransactionManager() {
		return new DataSourceTransactionManager(masterDataSource());
	}

	
}
