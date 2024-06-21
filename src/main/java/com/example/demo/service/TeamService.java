package com.example.demo.service;

import com.example.demo.webclient.RapidWebClient;
import com.example.demo.model.Team;
import com.example.demo.payload.TeamInformationResponse;
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

    public List<Team> retrieveTeams() {
        TeamInformationResponse teamInformationResponse = rapidWebClient.fetchTeams();
        List<Team> teams = teamInformationResponse.getResponse().stream()
                .map(TeamResponse::getTeam)
                .map(Team::new)
                .collect(toList());
        teamRepository.saveAll(teams);
        return teams;
    }

}
