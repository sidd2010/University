package com.example.Admin.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "Subjects")
public class Subject {
    @Id
    private Integer subjectId;
    private String subjectName;
    private Integer teacherId;

}
