package com.example.Admin.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi UniversityApi(){
        String[] paths=new String[]{"admin/**"};
        return GroupedOpenApi.builder().group("admin").pathsToMatch(paths).build();
    }

}
