package com.example.Teacher.handler;

import com.example.Teacher.entity.Teacher;
import com.example.Teacher.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(TeacherHandler.class)
class TeacherHandlerTest {

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetTeachers() {
        Teacher teacher1 = new Teacher(1, "John", "Doe", List.of(1, 2));
        Teacher teacher2 = new Teacher(2, "Jane", "Doe", List.of(3, 4));
        when(teacherService.getTeachers()).thenReturn(Flux.just(teacher1, teacher2));

        webTestClient.get()
                .uri("/teacher")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Teacher.class)
                .hasSize(2);

    }

    @Test
    void testGetTeacherById() {
        int teacherId = 1;
        Teacher teacher = new Teacher(teacherId, "John", "Doe", List.of(1, 2));
        when(teacherService.getTeacherById(teacherId)).thenReturn(Mono.just(teacher));

        webTestClient.get()
                .uri("/teacher/{teacherId}", teacherId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Teacher.class)
        ;

    }

    @Test
    void testAddTeacher() {
        Teacher teacher = new Teacher(1, "John", "Doe", List.of(1, 2));
        when(teacherService.addTeacher(any(Teacher.class))).thenReturn(Mono.just(teacher));

        webTestClient.post()
                .uri("/teacher")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(teacher)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Teacher.class);
    }

    @Test
    void testDeleteTeacher() {
        int teacherId = 1;
        when(teacherService.deleteTeacher(teacherId)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/teacher/{teacherId}", teacherId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdateTeacher() {
        int teacherId = 1;
        Teacher teacher = new Teacher(teacherId, "John", "Doe", List.of(1, 2));
        when(teacherService.updateTeacher(anyInt(), any(Teacher.class))).thenReturn(Mono.just(teacher));

        webTestClient.put()
                .uri("/teacher/{teacherId}", teacherId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(teacher)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Teacher.class);
    }
}
