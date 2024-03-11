package com.example.Teacher.router;

import com.example.Teacher.handler.TeacherHandler;
import com.example.Teacher.service.TeacherService;
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
public class TeacherRouter implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> routerTeacher(TeacherHandler teacherHandler){
        return route().GET("/teacher", accept(MediaType.APPLICATION_JSON),
                        teacherHandler::getTeachers,
                        ops->ops.beanClass(TeacherService.class).beanMethod("getTeachers")).build()
                .and(route().POST("/teacher",accept(MediaType.APPLICATION_JSON),
                                teacherHandler::addTeacher,
                                ops->ops.beanClass(TeacherService.class).beanMethod("addTeacher")).build()
                        .and(route().PUT("/teacher/{teacherId}",accept(MediaType.APPLICATION_JSON),
                                        teacherHandler::updateTeacher,
                                        ops->ops.beanClass(TeacherService.class).beanMethod("updateTeacher")).build()
                                .and(route().GET("/teacher/{teacherId}",accept(MediaType.APPLICATION_JSON),
                                                teacherHandler::getTeacherById,
                                                ops->ops.beanClass(TeacherService.class).beanMethod("getTeacherById")).build()
                                        .and(route().DELETE("/teacher/{teacherId}",accept(MediaType.APPLICATION_JSON),
                                                teacherHandler::deleteTeacher,
                                                ops->ops.beanClass(TeacherService.class).beanMethod("deleteTeacher")).build()
                                                .and(route().GET("/teacher/{firstName}",accept(MediaType.APPLICATION_JSON),
                                                        teacherHandler::getTeacherByFirstName,
                                                        ops-> ops.beanClass(TeacherService.class).beanMethod("getTeacherByFirstName")).build())))));
    }
}
