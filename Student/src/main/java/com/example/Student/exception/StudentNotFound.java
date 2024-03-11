package com.example.Student.exception;

public class StudentNotFound extends RuntimeException{
    private String message;

    public StudentNotFound(){
        super();
    }

    public StudentNotFound(String message) {
        super(message);
        this.message = message;
    }
}

