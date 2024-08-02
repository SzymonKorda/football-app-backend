package com.example.demo.controller;

import com.example.demo.payload.league.api.CreateLeagueRequest;
import com.example.demo.payload.league.api.FullLeagueResponse;
import com.example.demo.service.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/league")
public class LeagueController {
    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostMapping("/admin")
    public ResponseEntity<FullLeagueResponse> createLeague(@RequestBody CreateLeagueRequest request) {
        var league = FullLeagueResponse.from(leagueService.createLeague(request.leagueName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(league);
    }

    @GetMapping("/{leagueId}")
    public ResponseEntity<FullLeagueResponse> getLeague(@PathVariable Integer leagueId) {
        var league = FullLeagueResponse.from(leagueService.retrieveLeague(leagueId));
        return ResponseEntity.ok(league);
    }

}
