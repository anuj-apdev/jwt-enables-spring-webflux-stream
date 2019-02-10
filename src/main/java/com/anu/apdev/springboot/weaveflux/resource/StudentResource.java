package com.anu.apdev.springboot.weaveflux.resource;

import com.anu.apdev.springboot.weaveflux.repository.StudentsRepository;
import com.anu.apdev.springboot.weaveflux.model.Student;
import com.anu.apdev.springboot.weaveflux.model.StudentMarks;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/class")
public class StudentResource {

    private StudentsRepository studentsRepository;

    public StudentResource(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @GetMapping("/all")
    public Flux<Student> getAll() {
        return studentsRepository
                .findAll();
    }

    @GetMapping("/{id}")
    public Mono<Student> getId(@PathVariable("id") final String empId) {
        return studentsRepository.findById(empId);
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StudentMarks> getEvents(@PathVariable("id") final String empId) {
        return studentsRepository.findById(empId)
                .flatMapMany(student -> {
                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                    Flux<StudentMarks> employeeEventFlux =
                            Flux.fromStream(
                                    Stream.generate(() -> new StudentMarks(student,
                                            new Date())));
                    return Flux.zip(interval, employeeEventFlux)
                            .map(Tuple2::getT2); });
    }
}
