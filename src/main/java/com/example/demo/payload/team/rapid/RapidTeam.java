package com.example.demo.payload.team.rapid;

public record RapidTeam(
        Integer id,
        String name,
        String code,
        String country,
        Integer founded,
        Boolean national,
        String logo,
        Integer rapidId
) {
}