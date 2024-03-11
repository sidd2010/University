package com.example.Student.service;
import com.example.Student.dto.Result;
import com.example.Student.dto.StudentWithResult;
import com.example.Student.entity.Student;
import com.example.Student.exception.StudentNotFound;
import com.example.Student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
   /* @Autowired
    ResultClient resultClient;*/
    public Flux<Student> getStudents(){
        return studentRepository.findAll();
    }
    public Mono<Student> getStudentById(int studentId){
        return studentRepository.findById(studentId).switchIfEmpty(Mono.error(new StudentNotFound("There is no school with id: "+studentId)));
    }

    public Mono<Student> addStudent(Student studentToAdd){
        return studentRepository.save(studentToAdd);
    }
    public Mono<Void> deleteStudent(int studentId){
        return studentRepository.deleteById(studentId);
    }
    public Mono<Student> updateStudent(int studentId, Student student){
        return studentRepository.findById(studentId)
                .flatMap(s -> {
                    s.setFirstName(student.getFirstName());
                    s.setLastName(student.getLastName());
                    s.setSemester(student.getSemester());
                    s.setSubjectCodes(student.getSubjectCodes());
                    return studentRepository.save(s);
                });
    }
   /* public Mono<StudentWithResult> getStudentWithResult(int studentId) {
        return studentRepository.findById(studentId)
                .flatMap(student ->
                        Flux.fromIterable(student.getSubjectCodes())
                                .flatMap(subjectId ->
                                        resultClient.getResultBySubjectIdAndStudentId(subjectId, studentId))
                                .collectList()
                                .map(marksList -> {
                                    StudentWithResult studentWithResult = new StudentWithResult();
                                    studentWithResult.setStudent(student);
                                    studentWithResult.setMarks(marksList);
                                    return studentWithResult;
                                })
                );
    }*/
   public Mono<StudentWithResult> getStudentWithResult(int studentId) {
       WebClient webClient = WebClient.builder().baseUrl("http://localhost:8082").build();
       return studentRepository.findById(studentId)
               .flatMap(student ->
                       Flux.fromIterable(student.getSubjectCodes()).flatMap(subjectId ->
                                       webClient.get().uri("/admin/result/{subjectId}/{studentId}",
                                               subjectId, studentId).retrieve().bodyToMono(Integer.class)).collectList()
                               .map(marksList -> {
                                   StudentWithResult studentWithResult = new StudentWithResult();
                                   studentWithResult.setStudent(student);
                                   studentWithResult.setMarks(marksList);
                                   return studentWithResult;
                               }));

   }
}