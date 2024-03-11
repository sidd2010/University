package com.example.Student.handler;
import com.example.Student.dto.StudentWithResult;
import com.example.Student.entity.Student;
import com.example.Student.service.StudentService;
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
public class StudentHandler {
    @Autowired
    StudentService studentService;
    public Mono<ServerResponse> getStudents(ServerRequest serverRequest) {
        Flux<Student> StudentList = studentService.getStudents();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(StudentList, Student.class);
    }
    public Mono<ServerResponse> getStudentById(ServerRequest serverRequest) {
        int StudentId = Integer.parseInt(serverRequest.pathVariable("studentId"));
        Mono<Student> StudentById = studentService.getStudentById(StudentId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(StudentById, Student.class);
    }
    public Mono<ServerResponse> addStudent(ServerRequest serverRequest) {
        Mono<Student> studentTOAdd = serverRequest.bodyToMono(Student.class);
        return studentTOAdd.flatMap(result ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(studentService.addStudent(result),Student.class));
    }
    public Mono<ServerResponse> deleteStudent(ServerRequest serverRequest){
        int StudentId = Integer.parseInt(serverRequest.pathVariable("studentId"));
        Mono<Void> StudentDeleted = studentService.deleteStudent(StudentId);
        return ServerResponse.ok().body(StudentDeleted,Student.class);
    }
    public Mono<ServerResponse> updateStudent(ServerRequest serverRequest) {
        int StudentId = Integer.parseInt(serverRequest.pathVariable("studentId"));
        Mono<Student> StudentMono = serverRequest.bodyToMono(Student.class);
        return StudentMono.flatMap(s ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentService.updateStudent(StudentId, s), Student.class));
    }
    public Mono<ServerResponse> getStudentWithResult(ServerRequest serverRequest){
        int studentId=Integer.parseInt((serverRequest.pathVariable("studentId")));
        Mono<StudentWithResult> result = studentService.getStudentWithResult(studentId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, StudentWithResult.class);
    }
}