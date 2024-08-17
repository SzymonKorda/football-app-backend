package com.example.demo.controller;


import com.example.demo.payload.team.api.CreateTeamsRequest;
import com.example.demo.payload.team.api.FullTeamResponse;
import com.example.demo.payload.team.api.SimpleTeamResponse;
import com.example.demo.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/teams/admin")
    public ResponseEntity<List<FullTeamResponse>> createTeams(@RequestBody CreateTeamsRequest request) {
        List<FullTeamResponse> teams = teamService.createTeams(request.leagueName())
                .stream()
                .map(FullTeamResponse::from)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(teams);
    }

    @GetMapping("/teams")
    public ResponseEntity<List<SimpleTeamResponse>> getTeams() {
        List<SimpleTeamResponse> teams = teamService.retrieveTeams()
                .stream()
                .map(SimpleTeamResponse::from)
                .toList();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<FullTeamResponse> getTeam(@PathVariable Integer teamId) {
        var team = FullTeamResponse.from(teamService.retrieveTeam(teamId));
        return ResponseEntity.ok(team);
    }

}

