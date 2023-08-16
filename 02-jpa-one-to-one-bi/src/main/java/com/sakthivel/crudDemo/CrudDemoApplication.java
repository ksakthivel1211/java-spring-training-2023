package com.sakthivel.crudDemo;

import com.sakthivel.crudDemo.dao.AppDAO;
import com.sakthivel.crudDemo.entity.Instructor;
import com.sakthivel.crudDemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO)
	{
		return runner -> {
//			createInstructor(appDAO);
			findInstructorById(appDAO);

		};
	}

	private void findInstructorById(AppDAO appDAO) {
		int id = 1;
		Instructor tempInstructor = appDAO.findInstructorById(id);
		System.out.println("Instructor at id :"+ id + " is "+ tempInstructor);
		System.out.println("associated instructor details : "+ tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {
		Instructor instructor = new Instructor("sakthi","vel","sakthi@gmail.com");
		InstructorDetail instructorDetail = new InstructorDetail("http://www.youtube.com","sleeping");

		instructor.setInstructorDetail(instructorDetail);
		System.out.println("Saving instructor...");
		appDAO.save(instructor);
		System.out.println("Saved...");
	}
}
