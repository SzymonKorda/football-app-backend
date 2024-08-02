package com.example.demo.webclient;

import com.example.demo.payload.league.rapid.RapidLeagueInformationResponse;
import com.example.demo.payload.player.PlayerStatisticsResponse;
import com.example.demo.payload.team.rapid.RapidTeamInformationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RapidWebClient {

    private final WebClient webClient;

    public RapidWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public RapidTeamInformationResponse fetchTeams(Integer rapidId) {
        Mono<RapidTeamInformationResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("league", rapidId)
                        .queryParam("season", 2023)
                        .build()
                )
                .retrieve()
                .bodyToMono(RapidTeamInformationResponse.class);
        return response.block();
    }

    public RapidLeagueInformationResponse fetchLeague(String leagueName) {
        Mono<RapidLeagueInformationResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/leagues")
                        .queryParam("name", leagueName)
                        .build())
                .retrieve()
                .bodyToMono(RapidLeagueInformationResponse.class);
        return response.block();
    }

    public PlayerStatisticsResponse fetchPlayersByTeam(Integer rapidId, Integer page) {
        Mono<PlayerStatisticsResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("team", rapidId)
                        .queryParam("season", 2023)
                        .queryParam("page", page)
                        .build()
                )
                .retrieve()
                .bodyToMono(PlayerStatisticsResponse.class);
        return response.block();
    }

    public PlayerStatisticsResponse fetchPlayer(Integer rapidId) {
        Mono<PlayerStatisticsResponse> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("id", rapidId)
                        .queryParam("season", 2023)
                        .build())
                .retrieve()
                .bodyToMono(PlayerStatisticsResponse.class);
        return response.block();
    }
}
