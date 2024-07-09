package com.example.demo.controller;

import com.example.demo.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @CrossOrigin
    @PostMapping("/admin/players")
    public ResponseEntity<?> createPlayers() {
        return playerService.createPlayers(39);
    }
}
