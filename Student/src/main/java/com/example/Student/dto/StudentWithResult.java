package com.example.Student.dto;

import com.example.Student.entity.Student;
import lombok.*;

import java.util.List;

@Data
public class StudentWithResult {
    private Student student;
    private List<Integer> marks;
}
