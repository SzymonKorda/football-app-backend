package com.example.demo.exception;

public class TeamAlreadyExistsException extends RuntimeException{
    public TeamAlreadyExistsException() {
        super("Team already exists");
    }
}
