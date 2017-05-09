package com.jinfang.jpa;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinfang.jpa.domain.User;
import com.jinfang.jpa.repository.UserRepository;

@Service
public class UserService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository repository;

	public void testUpdate(){
		User user = repository.findOne(1L);
		log.info("User found with findOne(1L):");
		log.info("--------------------------------");
		log.info(user.toString());
		log.info("");
		user.setName("erwerw");
		
		repository.save(user);
		user = repository.findOne(1L);
		log.info("User found with findOne(1L):");
		log.info("--------------------------------");
		log.info(user.toString());
	}
	
	
	public void test() {
		// save a couple of Users
		
		User user=repository.save(new User("Bauer"));
		
		log.info(user.toString());
		repository.save(new User("O'Brian"));
		repository.save(new User("Bauer"));
		repository.save(new User("Palmer"));
		repository.save(new User("Dessler"));

		// fetch all Users
		log.info("Users found with findAll():");
		log.info("-------------------------------");
		for (User User : repository.findAll()) {
			log.info(User.toString());
		}
		log.info("exist id=3,{}",repository.exists(3L));

		// fetch an individual User by ID
		User User = repository.findOne(1L);
		log.info("User found with findOne(1L):");
		log.info("--------------------------------");
		log.info(User.toString());
		log.info("");

		// fetch Users by last name
		log.info("User found with findByLastName('Bauer'):");
		log.info("--------------------------------------------");
		for (User bauer : repository.findByName("Bauer")) {
			log.info(bauer.toString());
		}
		
		repository.delete(new User("Bauer"));
		repository.flush();
		log.info("User found with findByLastName('Bauer'):");
		log.info("--------------------------------------------");
		repository.findByName("Bauer").forEach(t->log.info(t.toString()));
		
		log.info("");
	}
	
	
}
