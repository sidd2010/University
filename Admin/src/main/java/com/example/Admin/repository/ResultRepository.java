package com.example.Admin.repository;

import com.example.Admin.entity.Result;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ResultRepository extends ReactiveMongoRepository<Result,Integer> {
    Mono<Result> findBySubjectIdAndStudentId(Integer subjectId , Integer studentId);

}
