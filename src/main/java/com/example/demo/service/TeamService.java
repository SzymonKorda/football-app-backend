package com.example.demo.service;

import com.example.demo.model.League;
import com.example.demo.model.Team;
import com.example.demo.payload.team.TeamResponse;
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

    public ResponseEntity<?> createTeams(String leagueName) {
        League league = leagueRepository.findByName(leagueName)
                .orElseThrow(() -> new RuntimeException("League does not exists"));
         if (teamRepository.existsByRapidId(league.getRapidId())) {
             return new ResponseEntity<>("Teams already exist", HttpStatus.OK);
         }

        List<Team> teams = rapidWebClient.fetchTeams(league.getRapidId())
                .getResponse().stream()
                .map(TeamResponse::getTeam)
                .map(teamDto -> new Team(teamDto, league))
                .toList();
        teamRepository.saveAll(teams);
        return new ResponseEntity<>("Teams added successfully", HttpStatus.CREATED);
    }

}
