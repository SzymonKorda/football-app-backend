package com.example.demo.payload.player;

import com.example.demo.model.Player;

public record FullPlayerResponse
        (Integer id,
         String name,
         String firstName,
         String lastName,
         Integer age,
         String nationality,
         String height,
         String weight,
         Boolean injured,
         String photoUrl,
         Integer rapidId,
         String teamName) {
    public static FullPlayerResponse from(Player player) {
        return new FullPlayerResponse(
                player.getId(),
                player.getName(),
                player.getFirstName(),
                player.getLastName(),
                player.getAge(),
                player.getNationality(),
                player.getHeight(),
                player.getWeight(),
                player.getInjured(),
                player.getPhotoUrl(),
                player.getRapidId(),
                player.getTeam().getName()
        );
    }
}
