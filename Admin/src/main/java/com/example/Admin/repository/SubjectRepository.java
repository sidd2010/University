package com.example.Admin.repository;

import com.example.Admin.entity.Subject;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SubjectRepository extends ReactiveMongoRepository<Subject,Integer> {
    Flux<Subject> findByTeacherId(Integer teacherId);
}
