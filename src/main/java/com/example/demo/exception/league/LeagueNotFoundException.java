package com.example.demo.exception.league;

import com.example.demo.exception.base.NotFoundException;

public class LeagueNotFoundException extends NotFoundException {
    public LeagueNotFoundException() {
        super("League doesn't exist");
    }
}
