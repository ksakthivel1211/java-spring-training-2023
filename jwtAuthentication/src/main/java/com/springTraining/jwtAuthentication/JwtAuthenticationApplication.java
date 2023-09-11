package com.springTraining.jwtAuthentication;

import com.springTraining.jwtAuthentication.Repository.UserRepository;
import com.springTraining.jwtAuthentication.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class JwtAuthenticationApplication {

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void initUsers(){
		List<User> users = Stream.of(
				new User(10,"sakthi","abc","sakthi@gmail.com"),
				new User(11,"sam","sam","sam@gmail.com"),
				new User(12,"naz","naz","naz@gmail.com"),
				new User(13,"ram","ram","ram@gmail.com")
		).collect(Collectors.toList());
		repository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationApplication.class, args);
	}

}
