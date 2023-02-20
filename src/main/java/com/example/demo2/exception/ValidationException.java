package com.example.demo2.exception;

public class ValidationException extends  RuntimeException{
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

}
