package com.example.demo.service;

import com.example.demo.model.League;
import com.example.demo.payload.league.LeagueResponse;
import com.example.demo.repository.LeagueRepository;
import com.example.demo.webclient.RapidWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final RapidWebClient rapidWebClient;


    public LeagueService(LeagueRepository leagueRepository, RapidWebClient rapidWebClient) {
        this.leagueRepository = leagueRepository;
        this.rapidWebClient = rapidWebClient;
    }

    public ResponseEntity<?> createLeague(String leagueName) {

        Optional<League> leagueOptional = leagueRepository.findByName(leagueName);
        if (leagueOptional.isPresent()) {
            return new ResponseEntity<>("League already exists", HttpStatus.OK);
        }

        League league = rapidWebClient.fetchLeague(leagueName)
                .getResponse().stream()
                .map(LeagueResponse::getLeague)
                .map(League::new)
                .findFirst().orElse(null);
        leagueRepository.save(league);
        return new ResponseEntity<>("League created", HttpStatus.CREATED);
    }

}
