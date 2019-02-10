package com.anu.apdev.springboot.weaveflux;

import com.anu.apdev.springboot.weaveflux.model.Student;
import com.anu.apdev.springboot.weaveflux.repository.StudentsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class JwtEnabledSpringWebFluxStream {

	@Bean
	CommandLineRunner employees(StudentsRepository studentsRepository) {
		return args -> {
			studentsRepository
					.deleteAll()
			.subscribe(null, null, () -> {
				Stream.of(new Student(UUID.randomUUID().toString(),
						"Peter", 23L),new Student(UUID.randomUUID().toString(),
						"Sam", 13L),new Student(UUID.randomUUID().toString(),
						"Ryan", 20L),new Student(UUID.randomUUID().toString(),
						"Chris", 53L)
						)
						.forEach(student -> {
				studentsRepository
						.save(student)
						.subscribe(System.out::println);
						});
			});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtEnabledSpringWebFluxStream.class, args);
	}
}
