package com.example.demo.webclient;

import com.example.demo.payload.league.LeagueInformationResponse;
import com.example.demo.payload.player.PlayerStatisticsResponse;
import com.example.demo.payload.team.TeamInformationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class RapidWebClient {
    @Value("${api_key}")
    private String apiKey;

    public TeamInformationResponse fetchTeams(Integer rapidId) {
        WebClient webClient = prepareWebClient();
        Mono<TeamInformationResponse> mono = webClient.get()
                .uri("https://api-football-v1.p.rapidapi.com/v3/teams?league=" + rapidId + "&season=2023")
                .retrieve()
                .bodyToMono(TeamInformationResponse.class);
        return mono.block();
    }

    public LeagueInformationResponse fetchLeague(String leagueName) {
        WebClient webClient = prepareWebClient();
        Mono<LeagueInformationResponse> mono = webClient.get()
                //TODO skorda: Find a way to make it working
//                .uri(uriBuilder -> uriBuilder
//                        .path("https://api-football-v1.p.rapidapi.com/v3/leagues")
//                        .queryParam("id", rapidId)
//                        .build())
                .uri("https://api-football-v1.p.rapidapi.com/v3/leagues?name=" + leagueName)
                .retrieve()
                .bodyToMono(LeagueInformationResponse.class);
        return mono.block();
    }

    public PlayerStatisticsResponse fetchPlayers(Integer rapidId) {
        WebClient webClient = prepareWebClient();
        Mono<PlayerStatisticsResponse> mono = webClient.get()
                .uri("https://api-football-v1.p.rapidapi.com/v3/players?league=" + rapidId + "&season=2023")
                .retrieve()
                .bodyToMono(PlayerStatisticsResponse.class);
        return mono.block();
    }

    //TODO skorda: Find way how to autowire web client as dependency
    private WebClient prepareWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-rapidapi-key", apiKey)
                .defaultHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .build();
    }
}
