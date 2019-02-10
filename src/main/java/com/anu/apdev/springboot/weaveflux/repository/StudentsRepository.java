package com.anu.apdev.springboot.weaveflux.repository;

import com.anu.apdev.springboot.weaveflux.model.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StudentsRepository extends ReactiveMongoRepository<Student, String> {
}
