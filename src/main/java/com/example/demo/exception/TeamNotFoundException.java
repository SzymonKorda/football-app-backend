package com.example.demo.exception;

public class TeamNotFoundException extends NotFoundException {
    public TeamNotFoundException() {
        super("Team doesn't exist");
    }
}
