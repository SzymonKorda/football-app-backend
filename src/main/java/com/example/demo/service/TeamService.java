package com.example.demo.service;

import com.example.demo.exception.LeagueNotFoundException;
import com.example.demo.exception.TeamAlreadyExistsException;
import com.example.demo.exception.TeamNotFoundException;
import com.example.demo.model.League;
import com.example.demo.model.Team;
import com.example.demo.payload.team.rapid.RapidTeamResponse;
import com.example.demo.repository.LeagueRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.webclient.RapidWebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final RapidWebClient rapidWebClient;

    public TeamService(TeamRepository teamRepository, LeagueRepository leagueRepository, RapidWebClient rapidWebClient) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.rapidWebClient = rapidWebClient;
    }

    public List<Team> createTeams(String leagueName) {
        League league = leagueRepository.findByName(leagueName)
                .orElseThrow(LeagueNotFoundException::new);

        if (teamRepository.existsByRapidId(league.getRapidId())) {
            throw new TeamAlreadyExistsException();
        }

        List<Team> teams = rapidWebClient.fetchTeams(league.getRapidId())
                .response()
                .stream()
                .map(RapidTeamResponse::team)
                .map(rapidTeam -> new Team(rapidTeam, league))
                .toList();

        return teamRepository.saveAll(teams);
    }

    public List<Team> retrieveTeams() {
        return teamRepository.findAll();
    }

    public Team getTeam(Integer teamId) {
        return this.teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    }

}
