package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class FirstSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository,
							 MongoTemplate mongoTemplate){
		return args -> {
			Address address = new Address(
					"Brazil",
					"Franca",
					"BR1"
			);
			String email = "iagom@gmail.com";

			Student student = new Student(
					"Iago",
					"Macarini",
					email,
					Gender.MALE,
					address,
					List.of("Computer Engineer", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now()
					);

			//Using mongoTemplate and Query
			//Using this class to verify if email is already in the document
			//Remember to use the Query by Mongo Core
			/*
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email));

			List<Student> students = mongoTemplate.find(query, Student.class);

			if(students.size() > 1){
				throw new IllegalStateException("Found many students with email " + email);
			}

			if(students.isEmpty()){
				System.out.println("Inserting student " + student);
				repository.insert(student);
			}else{
				System.out.println(student + "already exists");
			}
			*/

			//Using Repository
			repository.findStudentByEmail(email).ifPresentOrElse( s -> {
				System.out.println(s + "already exists");
			}, () -> {
				System.out.println("Inserting student " + student);
				repository.insert(student);
			});
		};
	}
}
