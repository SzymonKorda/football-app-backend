package com.example.demo.service;

import com.example.demo.model.Player;
import com.example.demo.payload.player.PlayerResponse;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.webclient.RapidWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final RapidWebClient rapidWebClient;


    public PlayerService(PlayerRepository playerRepository, RapidWebClient rapidWebClient) {
        this.playerRepository = playerRepository;
        this.rapidWebClient = rapidWebClient;
    }

    public ResponseEntity<?> createPlayers(Integer leagueId) {
        List<Player> players = rapidWebClient.fetchPlayers(leagueId)
                .getResponse()
                .stream()
                .map(PlayerResponse::getPlayer)
                .map(Player::new)
                .toList();

        playerRepository.saveAll(players);

        return new ResponseEntity<>("Players created successfully", HttpStatus.CREATED);
    }
}
