package com.example.demo.service;

import com.example.demo.exception.league.LeagueAlreadyExistsException;
import com.example.demo.exception.league.LeagueNotFoundException;
import com.example.demo.exception.rapid.RapidNotFoundException;
import com.example.demo.model.League;
import com.example.demo.payload.league.rapid.RapidLeagueResponse;
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

    public League createLeague(String leagueName) {
        checkIfLeagueAlreadyExists(leagueName);
        League league = fetchLeagueFromRapid(leagueName);
        return leagueRepository.save(league);
    }

    public League retrieveLeague(Integer leagueId) {
        return leagueRepository.findById(leagueId).orElseThrow(LeagueNotFoundException::new);
    }

    private League fetchLeagueFromRapid(String leagueName) {
        return rapidWebClient.fetchLeague(leagueName)
                .response()
                .stream()
                .map(RapidLeagueResponse::league)
                .map(League::new)
                .findAny()
                .orElseThrow(RapidNotFoundException::new);
    }

    private void checkIfLeagueAlreadyExists(String leagueName) {
        if (leagueRepository.existsByName(leagueName)) {
            throw new LeagueAlreadyExistsException();
        }
    }
}
