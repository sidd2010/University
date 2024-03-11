package com.example.user.service;

import com.example.user.model.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TeacherService {
    public Flux<Teacher> getTeachers() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081").build();
        return webClient.get().uri("/teacher").retrieve().bodyToFlux(Teacher.class);
    }
    public Mono<Teacher> addTeacher(Teacher teacher){
        WebClient webClient=WebClient.builder().baseUrl("http://localhost:8081").build();
        return webClient.post().uri("/teacher").body(Mono.just(teacher), Teacher.class)
                .retrieve().bodyToMono(Teacher.class);
    }
    public Mono<Teacher> updateTeacher(Integer teacherId, Teacher teacher){
        WebClient webClient= WebClient.builder().baseUrl("http://localhost:8081").build();
        return webClient.put().uri("/teacher/{teacherId}",teacherId).body(Mono.just(teacher),Teacher.class)
                .retrieve().bodyToMono(Teacher.class);
    }
}
