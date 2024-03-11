package com.example.Admin.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Result")
public class Result {
    @Id
    private Integer subjectId;
    private List<Integer> studentId;
    private List<Integer> marks;
}
