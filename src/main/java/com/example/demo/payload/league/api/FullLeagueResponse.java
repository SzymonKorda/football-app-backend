package com.example.demo.payload.league.api;

import com.example.demo.model.League;

public record FullLeagueResponse(
        Integer id,
        String name,
        String type,
        String logoUrl,
        Integer rapidId
) {
    public static FullLeagueResponse from(League league) {
        return new FullLeagueResponse(
                league.getId(),
                league.getName(),
                league.getType(),
                league.getLogoUrl(),
                league.getRapidId()
        );
    }
}
