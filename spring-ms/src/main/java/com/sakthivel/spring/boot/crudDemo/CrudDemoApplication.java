package com.sakthivel.spring.boot.crudDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.sakthivel.spring.boot.crudDemo.entity")
@EnableJpaRepositories("com.sakthivel.spring.boot.crudDemo.dao")
public class CrudDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(CrudDemoApplication.class, args);
	}

}
