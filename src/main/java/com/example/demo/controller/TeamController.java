package com.example.demo.controller;


import com.example.demo.model.Team;
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
    @GetMapping("/teams/league/{leagueId}")
    public ResponseEntity<?> getTeams(@PathVariable Integer leagueId) {
//        List<Team> teams = teamService.retrieveTeams(leagueId);
        return new ResponseEntity<>("teams", HttpStatus.OK);
    }

}

