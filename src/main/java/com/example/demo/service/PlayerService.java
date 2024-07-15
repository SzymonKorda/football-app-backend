package com.example.demo.service;

import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.payload.player.PlayerDto;
import com.example.demo.payload.player.PlayerPaging;
import com.example.demo.payload.player.PlayerResponse;
import com.example.demo.payload.player.PlayerStatisticsResponse;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.webclient.RapidWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;
    private final RapidWebClient rapidWebClient;
    private final TeamRepository teamRepository;


    public PlayerService(PlayerRepository playerRepository, TeamService teamService, RapidWebClient rapidWebClient, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
        this.rapidWebClient = rapidWebClient;
        this.teamRepository = teamRepository;
    }

    public ResponseEntity<?> createPlayer(Integer rapidId) {
        PlayerStatisticsResponse playerResponse = rapidWebClient.fetchPlayer(rapidId);
        Player player = new Player(playerResponse.getResponse().get(0).getPlayer());
        playerRepository.save(player);
        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }

    public ResponseEntity<?> createPlayers(Integer leagueId) {

        List<Team> teams = teamService.retrieveTeams();

        teams.forEach(team -> {
            PlayerStatisticsResponse response = rapidWebClient.fetchPlayersByTeam(team.getRapidId(), 1);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            PlayerPaging paging = response.getPaging();
            List<Player> players = response.getResponse().stream().map(PlayerResponse::getPlayer).map(Player::new).toList();
            List<Player> teamPlayers = new ArrayList<>(players);
            for (int i = 2; i <= paging.getTotal(); i++) {
                PlayerStatisticsResponse nextResponse = rapidWebClient.fetchPlayersByTeam(team.getRapidId(), i);
                List<Player> nextPlayers = nextResponse.getResponse().stream().map(PlayerResponse::getPlayer).map(Player::new).toList();
                teamPlayers.addAll(nextPlayers);

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            teamPlayers.forEach(p -> p.setTeam(team));
            playerRepository.saveAll(teamPlayers);
        });
        return new ResponseEntity<>("Players created successfully", HttpStatus.CREATED);
    }

    public List<Player> retrievePlayers() {
        return playerRepository.findAll();
    }
}
