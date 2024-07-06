package com.example.demo.service;

import com.example.demo.webclient.RapidWebClient;
import com.example.demo.model.Team;
import com.example.demo.payload.TeamResponse;
import com.example.demo.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final RapidWebClient rapidWebClient;


    public TeamService(TeamRepository teamRepository, RapidWebClient rapidWebClient) {
        this.teamRepository = teamRepository;
        this.rapidWebClient = rapidWebClient;
    }

    public List<Team> retrieveTeams(Integer leagueId) {
        return teamRepository.existsByRapidId(leagueId)
                ? teamRepository.findAllByRapidId(leagueId)
                : fetchAndSaveTeams(leagueId);
    }

    private List<Team> fetchAndSaveTeams(Integer rapidId) {
        List<Team> teams = rapidWebClient.fetchTeams(rapidId)
                .getResponse().stream()
                .map(TeamResponse::getTeam)
                .map(Team::new)
                .collect(toList());
        teamRepository.saveAll(teams);
        return teams;
    }

}
