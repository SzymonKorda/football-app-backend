package com.example.demo.service;

import com.example.demo.exception.league.LeagueNotFoundException;
import com.example.demo.exception.team.TeamAlreadyExistsException;
import com.example.demo.exception.team.TeamNotFoundException;
import com.example.demo.model.League;
import com.example.demo.model.Team;
import com.example.demo.payload.team.rapid.RapidTeamResponse;
import com.example.demo.repository.LeagueRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.webclient.RapidWebClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

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
        League league = checkIfLeagueExists(leagueName);
        List<Team> teams = fetchTeamsFromRapid(league);
        checkIfTeamsAlreadyExist(teams);
        return teamRepository.saveAll(teams);
    }

    public List<Team> retrieveTeams() {
        return teamRepository.findAll();
    }

    public Team retrieveTeam(Integer teamId) {
        return this.teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    }

    private void checkIfTeamsAlreadyExist(List<Team> teams) {
        teams.forEach(checkIfTeamAlreadyExists());
    }

    private Consumer<Team> checkIfTeamAlreadyExists() {
        return team -> {
            if (teamRepository.existsByRapidId(team.getRapidId())) {
                throw new TeamAlreadyExistsException();
            }
        };
    }

    private League checkIfLeagueExists(String leagueName) {
        return leagueRepository.findByName(leagueName).orElseThrow(LeagueNotFoundException::new);
    }

    private List<Team> fetchTeamsFromRapid(League league) {
        return rapidWebClient.fetchTeams(league.getRapidId())
                .response()
                .stream()
                .map(RapidTeamResponse::team)
                .map(rapidTeam -> new Team(rapidTeam, league))
                .toList();
    }
}
