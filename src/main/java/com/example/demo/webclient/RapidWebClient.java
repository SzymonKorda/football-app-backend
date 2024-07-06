package com.example.demo.webclient;

import com.example.demo.payload.TeamInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RapidWebClient{
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

    //TODO: skorda Find way how to autowire web client as dependency
    private WebClient prepareWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-rapidapi-key", apiKey)
                .defaultHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .build();
    }
}
