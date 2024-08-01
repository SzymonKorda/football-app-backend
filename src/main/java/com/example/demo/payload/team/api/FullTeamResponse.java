package com.example.demo.payload.team.api;

import com.example.demo.model.Team;

public record FullTeamResponse(
        Integer id,
        String name,
        String code,
        String country,
        Integer founded,
        Boolean national,
        String logoUrl,
        Integer rapidId
) {
    public static FullTeamResponse from(Team team) {
        return new FullTeamResponse(
                team.getId(),
                team.getName(),
                team.getCode(),
                team.getCountry(),
                team.getFounded(),
                team.getNational(),
                team.getLogoUrl(),
                team.getRapidId()
        );
    }
}
