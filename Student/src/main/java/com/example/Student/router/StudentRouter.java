package com.example.Student.router;
import com.example.Student.handler.StudentHandler;
import com.example.Student.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
@EnableWebFlux
@Configuration
public class StudentRouter implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> routerStudent(StudentHandler studentHandler){
        return route().GET("/student", accept(MediaType.APPLICATION_JSON),
                        studentHandler::getStudents,
                        ops->ops.beanClass(StudentService.class).beanMethod("getStudents")).build()
                .and(route().POST("/student",accept(MediaType.APPLICATION_JSON),
                                studentHandler::addStudent,
                                ops->ops.beanClass(StudentService.class).beanMethod("addStudent")).build()
                        .and(route().PUT("/student/{studentId}",accept(MediaType.APPLICATION_JSON),
                                        studentHandler::updateStudent,
                                        ops->ops.beanClass(StudentService.class).beanMethod("updateStudent")).build()
                                .and(route().GET("/student/{studentId}",accept(MediaType.APPLICATION_JSON),
                                                studentHandler::getStudentById,
                                                ops->ops.beanClass(StudentService.class).beanMethod("getStudentById")).build()
                                        .and(route().DELETE("/student/{studentId}",accept(MediaType.APPLICATION_JSON),
                                                studentHandler::deleteStudent,
                                                ops->ops.beanClass(StudentService.class).beanMethod("deleteStudent")).build()
                                                .and(route().GET("/student/result/{studentId}",accept(MediaType.APPLICATION_JSON),
                                                        studentHandler::getStudentWithResult,
                                                        ops->ops.beanClass(StudentService.class).beanMethod("getStudentWithResult")).build())))));
    }
}