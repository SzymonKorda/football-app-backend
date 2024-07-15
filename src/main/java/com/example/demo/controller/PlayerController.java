package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.payload.player.PlayerInformationResponseWithTeamName;
import com.example.demo.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @CrossOrigin
    @PostMapping("/admin/player")
    public ResponseEntity<?> createPlayer() {
        return playerService.createPlayer(276);
    }

    @CrossOrigin
    @GetMapping("/players")
    public ResponseEntity<?> getPlayers() {
        List<Player> players = playerService.retrievePlayers();
        List<PlayerInformationResponseWithTeamName> list = players.stream().map(PlayerInformationResponseWithTeamName::new).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
