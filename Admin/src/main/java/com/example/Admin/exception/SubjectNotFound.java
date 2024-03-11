package com.example.Admin.exception;

public class SubjectNotFound extends RuntimeException{
    private String message;

    public SubjectNotFound(){
        super();
    }

    public SubjectNotFound(String message) {
        super(message);
        this.message = message;
    }
}

