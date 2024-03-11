package com.example.Student.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi UniversityApi(){
        String[] paths=new String[]{"student/**"};
        return GroupedOpenApi.builder().group("student").pathsToMatch(paths).build();
    }
}
