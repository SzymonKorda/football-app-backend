package com.example.demo.controller;

import com.example.demo.model.League;
import com.example.demo.payload.league.CreateLeagueRequest;
import com.example.demo.service.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @CrossOrigin
    @PostMapping("/admin/league")
    public ResponseEntity<?> createLeague(@RequestBody CreateLeagueRequest request) throws InterruptedException {
        return leagueService.createLeague(request.getLeagueName());
    }

    @CrossOrigin
    @GetMapping("/league/{leagueId}")
    public ResponseEntity<?> getLeague(@PathVariable Integer leagueId) {
        League league = leagueService.retrieveLeague(leagueId);
        return new ResponseEntity<>(league, HttpStatus.OK);
    }

}
