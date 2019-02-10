package com.anu.apdev.springboot.weaveflux.handler;

import com.anu.apdev.springboot.weaveflux.model.Student;
import com.anu.apdev.springboot.weaveflux.model.StudentMarks;
import com.anu.apdev.springboot.weaveflux.repository.StudentsRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class StudentsHandler {

    private StudentsRepository studentsRepository;

    public StudentsHandler(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .body(
                        studentsRepository.findAll(), Student.class
                );
    }

    public Mono<ServerResponse> getId(ServerRequest serverRequest) {

        String empId = serverRequest.pathVariable("id");
        return ServerResponse
                .ok()
                .body(
                        studentsRepository.findById(empId), Student.class
                );
    }

    public Mono<ServerResponse> getEvents(ServerRequest serverRequest) {
        String empId = serverRequest.pathVariable("id");
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        studentsRepository.findById(empId)
                                .flatMapMany(student -> {
                                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                                    Flux<StudentMarks> studentMarksFlux =
                                            Flux.fromStream(
                                                    Stream.generate(() -> new StudentMarks(student,
                                                            new Date()))
                                            );
                                    return Flux.zip(interval, studentMarksFlux)
                                            .map(Tuple2::getT2);
                                }), StudentMarks.class
                );
    }
}