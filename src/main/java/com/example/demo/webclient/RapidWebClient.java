package com.example.demo.webclient;

import com.example.demo.payload.TeamInformationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RapidWebClient {
    @Value("${api_key}")
    private String apiKey;

    private final WebClient webClient;

    public RapidWebClient() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-rapidapi-key", apiKey)
                .defaultHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .build();
    }

    public TeamInformationResponse fetchTeams() {
        Mono<TeamInformationResponse> mono = webClient.get()
                .uri("https://api-football-v1.p.rapidapi.com/v3/teams?league=39&season=2023")
                .retrieve()
                .bodyToMono(TeamInformationResponse.class);
        return mono.block();
    }
}
