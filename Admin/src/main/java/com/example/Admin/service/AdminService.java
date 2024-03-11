package com.example.Admin.service;
import com.example.Admin.entity.Result;
import com.example.Admin.entity.Subject;
import com.example.Admin.exception.ResultNotFound;
import com.example.Admin.exception.SubjectNotFound;
import com.example.Admin.repository.ResultRepository;
import com.example.Admin.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class AdminService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ResultRepository resultRepository;
    public Flux<Subject> getSubjects() {
        return subjectRepository.findAll();
    }
    public Mono<Subject> getSubjectById(int subjectId) {
        return subjectRepository.findById(subjectId).switchIfEmpty(Mono.error(new SubjectNotFound("There is no school with id: " + subjectId)));
    }

    public Mono<Subject> addSubject(Subject subjectToAdd) {
        return subjectRepository.save(subjectToAdd);
    }

    public Mono<Void> deleteSubject(int subjectId) {
        return subjectRepository.deleteById(subjectId);
    }

    public Mono<Subject> updateSubject(int subjectId, Subject subject) {
        return subjectRepository.findById(subjectId)
                .flatMap(s -> {
                    s.setSubjectName(subject.getSubjectName());
                    s.setTeacherId(subject.getTeacherId());
                    return subjectRepository.save(s);
                });
    }

    public Flux<Result> getResult() {
        return resultRepository.findAll();
    }

    public Mono<Result> getResultById(int subjectId) {
        return resultRepository.findById(subjectId).switchIfEmpty(Mono.error(new ResultNotFound("There is no school with id: " + subjectId)));
    }

    public Mono<Result> addResult(Result resultToAdd) {
        List<Integer> marks = new ArrayList<>(Collections.nCopies(resultToAdd.getStudentId().size(), 0));
        resultToAdd.setMarks(marks);
        return resultRepository.save(resultToAdd);
    }

    public Mono<Void> deleteResult(int subjectId) {
        return resultRepository.deleteById(subjectId);
    }

    public Mono<Result> updateResult(int subjectId, Result result) {
        return resultRepository.findById(subjectId)
                .flatMap(s -> {
                    s.setStudentId(result.getStudentId());
                    s.setMarks(result.getMarks());
                    return resultRepository.save(s);
                });
    }

    public Mono<Integer> getResultBySubjectIdAndStudentId(int subjectId, int studentId) {
        return resultRepository.findBySubjectIdAndStudentId(subjectId, studentId)
                .map(result -> {
                    Integer marks = 0;
                    for (int i = 0; i < result.getStudentId().size(); i++) {
                        if (result.getStudentId().get(i) == studentId) {
                            marks=result.getMarks().get(i);
                            break;
                        }
                    }
                    return marks;
                });
    }
}