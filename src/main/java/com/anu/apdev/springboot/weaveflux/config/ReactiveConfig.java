package com.anu.apdev.springboot.weaveflux.config;

import com.anu.apdev.springboot.weaveflux.handler.StudentsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class ReactiveConfig {
    @Bean
    RouterFunction<?> routerFunction(StudentsHandler studentsHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/event/school/all"), studentsHandler::getAll)
                .andRoute(RequestPredicates.GET("/event/school/{id}"), studentsHandler::getId)
                .andRoute(RequestPredicates.GET("/event/school/{id}/events"), studentsHandler::getEvents);
    }
}