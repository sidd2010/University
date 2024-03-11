package com.example.user.model;
import lombok.Data;

import java.util.List;

@Data
public class StudentWithResult {
    private Student student;
    private List<Integer> marks;
}
