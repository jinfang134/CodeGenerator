package com.jinfang.jpa;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class JpaApplication implements ApplicationRunner{
	private static final Logger log = LoggerFactory.getLogger(JpaApplication.class);

	@Autowired
	UserService service;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		service.testUpdate();
	}

	
}
