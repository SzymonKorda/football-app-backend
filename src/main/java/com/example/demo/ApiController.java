package com.example.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${api_key}")
    private String apiKey;

    private final TeamRepository teamRepository;

    public ApiController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/teams")
    public String getTeams() {
        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-rapidapi-key", apiKey)
                .defaultHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .build();

        Mono<TeamInformationResponse> mono = webClient.get()
                .uri("https://api-football-v1.p.rapidapi.com/v3/teams?league=39&season=2023")
                .retrieve()
                .bodyToMono(TeamInformationResponse.class);

        TeamInformationResponse teamInformationResponse = mono.block();

        List<Team> teams = teamInformationResponse.getResponse().stream()
                .map(TeamResponse::getTeam)
                .map(Team::new)
                .collect(toList());


        teamRepository.saveAll(teams);
        return null;
    }

}
