package com.example.demo.exception;

public class LeagueNotFoundException extends NotFoundException {
    public LeagueNotFoundException() {
        super("League doesn't exist");
    }
}
