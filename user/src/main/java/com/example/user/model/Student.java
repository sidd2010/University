package com.example.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private Integer studentId;
    private String firstName;
    private String lastName;
    private Integer semester;
    private List<Integer> subjectCodes;

}
