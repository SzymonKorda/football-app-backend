package com.example.demo.service;

import com.example.demo.model.League;
import com.example.demo.payload.LeagueResponse;
import com.example.demo.repository.LeagueRepository;
import com.example.demo.webclient.RapidWebClient;
import org.springframework.stereotype.Service;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final RapidWebClient rapidWebClient;


    public LeagueService(LeagueRepository leagueRepository, RapidWebClient rapidWebClient) {
        this.leagueRepository = leagueRepository;
        this.rapidWebClient = rapidWebClient;
    }

    public League retrieveLeague(Integer leagueId) {
        return leagueRepository.existsByRapidId(leagueId)
                ? leagueRepository.findByRapidId(leagueId)
                : fetchAndSaveLeague(leagueId);
    }

    private League fetchAndSaveLeague(Integer rapidId) {
        League league = rapidWebClient.fetchLeague(rapidId)
                .getResponse().stream()
                .map(LeagueResponse::getLeague)
                .map(League::new)
                .findFirst().orElse(null);
        leagueRepository.save(league);
        return league;
    }

}
