package com.code.springboot.firstrestapi.user;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner{

	
	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		super();
		this.repository = repository;
	}


	private Logger logger = LoggerFactory.getLogger(getClass());

	private UserDetailsRepository repository;
	
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//logger.info(Arrays.toString(args));
		
		repository.save(new UserDetails("Rahul","Admin"));
		repository.save(new UserDetails("Ravi","Admin"));
		repository.save(new UserDetails("John","developer"));
		
		List<UserDetails> users = repository.findAll();
		
		 users.forEach(u -> logger.info(u.toString()));
		
		 List<UserDetails> users1 =repository.findByRole("developer");
		 users1.forEach(u -> logger.info(u.toString()));
		
	}

	
	
}
