package com.example.Teacher.exception;

public class TeacherNotFound extends RuntimeException{
    private String message;

    public TeacherNotFound(){
        super();
    }

    public TeacherNotFound(String message) {
        super(message);
        this.message = message;
    }
}

