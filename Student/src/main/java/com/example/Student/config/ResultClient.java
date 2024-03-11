/*package com.example.Student.config;

import com.example.Student.dto.Result;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@FeignClient(name="ResultService" ,url = "http://localhost:8082")
public interface ResultClient {
    @RequestMapping(value="/admin/getResult/{subjectId}/{studentId}",consumes = "application/json", produces = "application/json")

    public Mono<Integer> getResultBySubjectIdAndStudentId(@PathVariable(value = "subjectId") int subjectId, @PathVariable(value ="studentId") int studentId);
}*/
