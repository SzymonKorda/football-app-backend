package com.example.demo.exception.team;

import com.example.demo.exception.base.AlreadyExistsException;

public class TeamAlreadyExistsException extends AlreadyExistsException {
    public TeamAlreadyExistsException() {
        super("Team already exists");
    }
}
