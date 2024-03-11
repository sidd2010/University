package com.example.Admin.router;

import com.example.Admin.handler.AdminHandler;
import com.example.Admin.service.AdminService;
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
public class AdminRouter implements WebFluxConfigurer {
    @Bean
    public RouterFunction<ServerResponse> routerAdmin(AdminHandler adminHandler){
        return route().GET("/admin/subject", accept(MediaType.APPLICATION_JSON),
                        adminHandler::getSubjects,
                        ops->ops.beanClass(AdminService.class).beanMethod("getSubjects")).build()
                .and(route().POST("/admin/subject",accept(MediaType.APPLICATION_JSON),
                        adminHandler::addSubject,
                        ops->ops.beanClass(AdminService.class).beanMethod("addSubject")).build()
                        .and(route().PUT("/admin/subject/{subjectId}",accept(MediaType.APPLICATION_JSON),
                                adminHandler::updateSubject,
                                ops->ops.beanClass(AdminService.class).beanMethod("updateSubject")).build()
                                .and(route().GET("/admin/subject/{subjectId}",accept(MediaType.APPLICATION_JSON),
                                        adminHandler::getSubjectById,
                                        ops->ops.beanClass(AdminService.class).beanMethod("getSubjectById")).build()
                                        .and(route().DELETE("/admin/subject/{subjectId}",accept(MediaType.APPLICATION_JSON),
                                                adminHandler::deleteSubject,
                                                ops->ops.beanClass(AdminService.class).beanMethod("deleteSubject")).build()))));
    }
    @Bean
    public RouterFunction<ServerResponse> routerResult(AdminHandler adminHandler){
        return route().GET("/admin/result", accept(MediaType.APPLICATION_JSON),
                        adminHandler::getResult,
                        ops->ops.beanClass(AdminService.class).beanMethod("getResult")).build()
                .and(route().POST("/admin/result",accept(MediaType.APPLICATION_JSON),
                                adminHandler::addResult,
                                ops->ops.beanClass(AdminService.class).beanMethod("addResult")).build()
                        .and(route().PUT("/admin/result/{subjectId}",accept(MediaType.APPLICATION_JSON),
                                        adminHandler::updateResult,
                                        ops->ops.beanClass(AdminService.class).beanMethod("updateResult")).build()
                                .and(route().GET("/admin/result/{subjectId}",accept(MediaType.APPLICATION_JSON),
                                                adminHandler::getResultById,
                                                ops->ops.beanClass(AdminService.class).beanMethod("getResultById")).build()
                                        .and(route().DELETE("/admin/result/{subjectId}",accept(MediaType.APPLICATION_JSON),
                                                adminHandler::deleteResult,
                                                ops->ops.beanClass(AdminService.class).beanMethod("deleteResult")).build()
                                                .and(route().GET("/admin/result/{subjectId}/{studentId}",accept(MediaType.APPLICATION_JSON),
                                                        adminHandler::getResultBySubjectIdAndStudentId,
                                                        ops->ops.beanClass(AdminService.class).beanMethod("getResultBySubjectIdAndStudentId")).build())))));
    }
}
