package com.example.Teacher.repository;

import com.example.Teacher.entity.Teacher;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface TeacherRepository extends ReactiveMongoRepository<Teacher,Integer> {
    @Query("{firstName:{ $regex : ?0}}")
    Flux<Teacher> getTeacherByFirstName(String firstName);
}
