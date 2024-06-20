package com.example.demo;


import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    public String getTeams() throws IOException, ParseException {
        // reading data from json file
//        String pathString = Paths.get("teams-information.json").toString();
//        JSONParser parser = new JSONParser();
//        return (JSONObject) parser.parse(new FileReader(pathString));

        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-rapidapi-key", apiKey)
                .defaultHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .build();

        Mono<Object> mono = webClient.get()
                .uri("https://api-football-v1.p.rapidapi.com/v3/teams?league=39&season=2023")
                .retrieve()
                .bodyToMono(Object.class);

        LinkedHashMap<?, ?> block = (LinkedHashMap<?, ?>) mono.block();

        ArrayList<LinkedHashMap<?, ?>> response = (ArrayList<LinkedHashMap<?, ?>>) block.get("response");
        List<LinkedHashMap<?, ?>> team = response.stream()
                .map(e -> ((LinkedHashMap<?, ?>) e.get("team")))
                .collect(Collectors.toList());

        List<TeamDto> teamDtos = team.stream()
                .map(t -> new TeamDto(
                        (Integer) t.get("id"),
                        (String) t.get("name"),
                        (String) t.get("code"),
                        (String) t.get("country"),
                        (Integer) t.get("founded"),
                        (Boolean) t.get("national"),
                        (String) t.get("logo")))
                .toList();

        List<Team> teams = teamDtos.stream()
                .map(dto -> new Team(
                        dto.getId(),
                        dto.getName(),
                        dto.getCode(),
                        dto.getCountry(),
                        dto.getFounded(),
                        dto.getNational(),
                        dto.getLogoUrl()))
                .toList();

        teamRepository.saveAll(teams);
        return null;
    }

}
