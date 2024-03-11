package com.example.Teacher.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi UniversityApi(){
        String[] paths=new String[]{"teacher/**"};
        return GroupedOpenApi.builder().group("teacher").pathsToMatch(paths).build();
    }
}
