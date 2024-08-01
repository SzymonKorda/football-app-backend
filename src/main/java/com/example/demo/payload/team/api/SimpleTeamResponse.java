package com.example.demo.payload.team.api;

import com.example.demo.model.Team;

public record SimpleTeamResponse(
        Integer id,
        String name,
        String code,
        String country,
        Integer founded,
        String logoUrl
) {
    public static SimpleTeamResponse from(Team team) {
        return new SimpleTeamResponse(
                team.getId(),
                team.getName(),
                team.getCode(),
                team.getCountry(),
                team.getFounded(),
                team.getLogoUrl()
        );
    }
}