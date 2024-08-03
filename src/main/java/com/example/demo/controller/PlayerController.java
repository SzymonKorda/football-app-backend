package com.example.demo.controller;

import com.example.demo.payload.player.FullPlayerResponse;
import com.example.demo.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createPlayers() {
        return playerService.createPlayers(39);
    }

    @GetMapping
    public ResponseEntity<?> getPlayers() {
        var players = playerService.retrievePlayers()
                .stream()
                .map(FullPlayerResponse::from)
                .toList();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<FullPlayerResponse> getPlayers(@PathVariable Integer playerId) {
        var player = FullPlayerResponse.from(playerService.getPlayer(playerId));
        return ResponseEntity.ok(player);
    }
}
