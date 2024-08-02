package com.example.demo.exception.league;

import com.example.demo.exception.base.AlreadyExistsException;

public class LeagueAlreadyExistsException extends AlreadyExistsException {
    public LeagueAlreadyExistsException() {
        super("League already exists");
    }
}
