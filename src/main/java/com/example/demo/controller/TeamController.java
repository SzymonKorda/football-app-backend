package com.example.demo.controller;


import com.example.demo.model.Team;
import com.example.demo.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public ResponseEntity<?> getTeams() {
        List<Team> teams = teamService.retrieveTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

}
