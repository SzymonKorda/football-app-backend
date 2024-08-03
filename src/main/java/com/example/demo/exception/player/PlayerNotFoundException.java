package com.example.demo.exception.player;

import com.example.demo.exception.base.NotFoundException;

public class PlayerNotFoundException extends NotFoundException {
    public PlayerNotFoundException() {
        super("Player doesn't exist");
    }
}
