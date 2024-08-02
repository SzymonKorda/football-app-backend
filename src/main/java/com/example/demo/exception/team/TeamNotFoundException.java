package com.example.demo.exception.team;

import com.example.demo.exception.base.NotFoundException;

public class TeamNotFoundException extends NotFoundException {
    public TeamNotFoundException() {
        super("Team doesn't exist");
    }
}
