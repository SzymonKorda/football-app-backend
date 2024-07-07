package com.example.demo.service;

import com.example.demo.model.League;
import com.example.demo.repository.LeagueRepository;
import com.example.demo.webclient.RapidWebClient;
import com.example.demo.model.Team;
import com.example.demo.payload.team.TeamResponse;
import com.example.demo.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

//    public List<Team> retrieveTeams(Integer teamId) {
//
//        League league = leagueRepository.findByRapidId(teamId);
//
//        return teamRepository.existsByRapidId(teamId)
//                ? teamRepository.findAllByRapidId(teamId)
//                : fetchAndSaveTeams(teamId, league);
//    }

    private List<Team> fetchAndSaveTeams(Integer rapidId, League league) {
        List<Team> teams = rapidWebClient.fetchTeams(rapidId)
                .getResponse().stream()
                .map(TeamResponse::getTeam)
                .map(teamDto -> new Team(teamDto, league))
                .collect(toList());
        teamRepository.saveAll(teams);
        return teams;
    }

}
