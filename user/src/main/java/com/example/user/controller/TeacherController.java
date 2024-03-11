package com.example.user.controller;
import com.example.user.model.Teacher;
import com.example.user.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @GetMapping("/teacher")
    public Mono<ResponseEntity<Flux<Teacher>>> getTeachers() {
        Flux<Teacher> teacherList = teacherService.getTeachers();
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(teacherList));
    }
    @PostMapping("/teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> addTeacher(@RequestBody Mono<Teacher> teacherMono) {
        return teacherMono.flatMap(result ->
                teacherService.addTeacher(result)
                        .map(savedTeacher ->
                                ResponseEntity.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(savedTeacher)
                        )
        );
    }
    @PutMapping("/teacher/{teacherId}")
    public Mono<ResponseEntity<?>> updateTeacher(
            @PathVariable Integer teacherId,
            @RequestBody Mono<Teacher> teacherMono) {
        return teacherMono.flatMap(result ->
                teacherService.updateTeacher(teacherId, result)
                        .map(updatedTeacher ->
                                ResponseEntity.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(updatedTeacher)
                        )
        );
    }
}