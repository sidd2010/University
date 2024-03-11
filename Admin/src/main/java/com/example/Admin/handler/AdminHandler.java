package com.example.Admin.handler;

import com.example.Admin.entity.Result;
import com.example.Admin.entity.Subject;
import com.example.Admin.service.AdminService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;

@Component
public class AdminHandler {
    @Autowired
    AdminService adminService;

    public Mono<ServerResponse> getSubjects(ServerRequest serverRequest) {
        Flux<Subject> subjectList = adminService.getSubjects();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(subjectList, Subject.class);
    }


    public Mono<ServerResponse> getSubjectById(ServerRequest serverRequest) {
        int subjectId = Integer.parseInt(serverRequest.pathVariable("subjectId"));
        Mono<Subject> subjectById = adminService.getSubjectById(subjectId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(subjectById, Subject.class);
    }


    public Mono<ServerResponse> addSubject(ServerRequest serverRequest) {
        Mono<Subject> subjectTOAdd = serverRequest.bodyToMono(Subject.class);
        return subjectTOAdd.flatMap(result ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(adminService.addSubject(result),Subject.class));
    }
    public Mono<ServerResponse> deleteSubject(ServerRequest serverRequest){
        int SubjectId = Integer.parseInt(serverRequest.pathVariable("subjectId"));
        Mono<Void> subjectDeleted = adminService.deleteSubject(SubjectId);
        return ServerResponse.ok().body(subjectDeleted,Subject.class);
    }

    public Mono<ServerResponse> updateSubject(ServerRequest serverRequest) {
        int subjectId = Integer.parseInt(serverRequest.pathVariable("subjectId"));
        Mono<Subject> subjectMono = serverRequest.bodyToMono(Subject.class);
        return subjectMono.flatMap(s ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(adminService.updateSubject(subjectId, s), Subject.class));
    }
    public Mono<ServerResponse> getResult(ServerRequest serverRequest) {
        Flux<Result> result = adminService.getResult();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, Result.class);
    }


    public Mono<ServerResponse> getResultById(ServerRequest serverRequest) {
        int subjectId = Integer.parseInt(serverRequest.pathVariable("subjectId"));
        Mono<Result> resultById = adminService.getResultById(subjectId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(resultById, Result.class);
    }


    public Mono<ServerResponse> addResult(ServerRequest serverRequest) {
        Mono<Result> resultTOAdd = serverRequest.bodyToMono(Result.class);
        return resultTOAdd.flatMap(result ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(adminService.addResult(result),Result.class));
    }
    public Mono<ServerResponse> deleteResult(ServerRequest serverRequest){
        int subjectId = Integer.parseInt(serverRequest.pathVariable("subjectId"));
        Mono<Void> resultDeleted = adminService.deleteResult(subjectId);
        return ServerResponse.ok().body(resultDeleted,Result.class);
    }

    public Mono<ServerResponse> updateResult(ServerRequest serverRequest) {
        int subjectId = Integer.parseInt(serverRequest.pathVariable("subjectId"));
        Mono<Result> StudentMono = serverRequest.bodyToMono(Result.class);
        return StudentMono.flatMap(s ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(adminService.updateResult(subjectId, s), Result.class));
    }
    public Mono<ServerResponse> getResultBySubjectIdAndStudentId(ServerRequest serverRequest){
        int subjectId= Integer.parseInt(serverRequest.pathVariable("subjectId"));
        int studentId= Integer.parseInt(serverRequest.pathVariable("studentId"));
        Mono<Integer> result = adminService.getResultBySubjectIdAndStudentId(subjectId,studentId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, Integer.class);
    }
}
