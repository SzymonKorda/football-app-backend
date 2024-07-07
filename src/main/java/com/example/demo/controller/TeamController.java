package com.example.demo.controller;


import com.example.demo.payload.team.CreateTeamsRequest;
import com.example.demo.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

