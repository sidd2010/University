package com.example.Admin.exception;

public class ResultNotFound extends RuntimeException{
    private String message;

    public ResultNotFound(){
        super();
    }

    public ResultNotFound(String message) {
        super(message);
        this.message = message;
    }
}

