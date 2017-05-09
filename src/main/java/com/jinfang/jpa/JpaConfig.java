package com.jinfang.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactorySecondary", 
transactionManagerRef = "transactionManagerSecondary", 
basePackages = { "com.jinfang.jpa.repository" })
public class JpaConfig {
	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	@Qualifier("dataSource")
	private DataSource secondaryDS;

	@Bean(name = "entityManagerSecondary")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactorySecondary(builder).getObject()
				.createEntityManager();
	}

	@Bean(name = "entityManagerFactorySecondary")
	public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(secondaryDS)
				.properties(getVendorProperties(secondaryDS))
				.packages("com.jinfang.jpa.domain")
				.persistenceUnit("secondaryPersistenceUnit").build();
	}

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getHibernateProperties(dataSource);
	}

	@Bean(name = "transactionManagerSecondary")
	PlatformTransactionManager transactionManagerSecondary(
			EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactorySecondary(builder)
				.getObject());
	}
}
