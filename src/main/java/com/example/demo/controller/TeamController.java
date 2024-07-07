package com.example.demo.controller;


import com.example.demo.model.League;
import com.example.demo.model.Team;
import com.example.demo.payload.team.CreateTeamsRequest;
import com.example.demo.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @CrossOrigin
    @PostMapping("/admin/team")
    public ResponseEntity<?> createTeams(@RequestBody CreateTeamsRequest request) {
       return teamService.createTeams(request.getLeagueName());
    }

    @CrossOrigin
    @GetMapping("/teams")
    public ResponseEntity<?> getTeams() {
        List<Team> teams = teamService.retrieveTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

}

