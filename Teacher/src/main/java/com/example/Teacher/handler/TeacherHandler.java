package com.example.Teacher.handler;
import com.example.Teacher.entity.Teacher;
import com.example.Teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static javax.security.auth.callback.ConfirmationCallback.OK;
@Component
public class TeacherHandler {
    @Autowired
    TeacherService teacherService;
    public Mono<ServerResponse> getTeachers(ServerRequest serverRequest) {
        Flux<Teacher> TeacherList = teacherService.getTeachers();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(TeacherList, Teacher.class);
    }
    public Mono<ServerResponse> getTeacherById(ServerRequest serverRequest) {
        int teacherId = Integer.parseInt(serverRequest.pathVariable("teacherId"));
        Mono<Teacher> teacherById = teacherService.getTeacherById(teacherId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(teacherById, Teacher.class);
    }
    public Mono<ServerResponse> addTeacher(ServerRequest serverRequest) {
        Mono<Teacher> teacherTOAdd = serverRequest.bodyToMono(Teacher.class);
        return teacherTOAdd.flatMap(result ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(teacherService.addTeacher(result),Teacher.class));
    }
    public Mono<ServerResponse> deleteTeacher(ServerRequest serverRequest){
        int teacherId = Integer.parseInt(serverRequest.pathVariable("teacherId"));
        Mono<Void> teacherDeleted = teacherService.deleteTeacher(teacherId);
        return ServerResponse.ok().body(teacherDeleted,Teacher.class);
    }
    public Mono<ServerResponse> updateTeacher(ServerRequest serverRequest) {
        int teacherId = Integer.parseInt(serverRequest.pathVariable("teacherId"));
        Mono<Teacher> teacherMono = serverRequest.bodyToMono(Teacher.class);
        return teacherMono.flatMap(s ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(teacherService.updateTeacher(teacherId, s), Teacher.class));
    }
    public Mono<ServerResponse> getTeacherByFirstName(ServerRequest serverRequest){
        String firstName= serverRequest.pathVariable("firstName");
        Flux<Teacher> teacherFlux=teacherService.getTeacherByFirstName(firstName);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(teacherFlux, Teacher.class);
    }
}