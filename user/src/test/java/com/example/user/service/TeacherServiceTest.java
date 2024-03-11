package com.example.user.service;

import com.example.user.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)

public class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @MockBean
    private WebClient.Builder webClientBuilder;

    @Test
    public void testGetTeachers() {
  //      webClientBuilder.baseUrl("http://localhost:8081").build();
        List<Teacher> teachers;
        teachers = Arrays.asList(new Teacher(1, "John", "Doe", List.of(1, 2)),
                new Teacher(2, "Jane", "Doe", List.of(3, 4)));
        Flux<Teacher> teacherFlux = Flux.fromIterable(teachers);
        when(webClientBuilder.build().get().uri("/teacher").retrieve().bodyToFlux(Teacher.class))
                .thenReturn(teacherFlux);

        Flux<Teacher> result = teacherService.getTeachers();

        StepVerifier.create(result.count())
                .expectNext(2L)
                .expectComplete()
                .verify();
    }

    @Test
    public void testAddTeacher() {
//        webClientBuilder.baseUrl("http://localhost:8081").build();

        Teacher teacher = new Teacher(1, "John", "Doe", List.of(1, 2));
        Mono<Teacher> teacherMono = Mono.just(teacher);
        when(webClientBuilder.build().post().uri("/teacher").body(Mono.just(teacher), Teacher.class)
                .retrieve().bodyToMono(Teacher.class))
                .thenReturn(teacherMono);

        Mono<Teacher> result = teacherService.addTeacher(teacher);

        StepVerifier.create(result)
                .expectNext(teacher)
                .expectComplete()
                .verify();
    }

    @Test
    public void testUpdateTeacher() {
      //  webClientBuilder.baseUrl("http://localhost:8081").build();

        Teacher teacher = new Teacher(1, "John", "Doe", List.of(1, 2));
        Mono<Teacher> teacherMono = Mono.just(teacher);
        when(webClientBuilder.build().put().uri("/teacher/1", 1).body(Mono.just(teacher), Teacher.class)
                .retrieve().bodyToMono(Teacher.class))
                .thenReturn(teacherMono);

        Mono<Teacher> result = teacherService.updateTeacher(1, teacher);

        StepVerifier.create(result)
                .expectNext(teacher)
                .expectComplete()
                .verify();
    }
}
