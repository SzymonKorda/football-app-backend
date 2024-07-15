package com.example.demo.payload.player;

import com.example.demo.model.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullPlayerResponse {
    private Integer id;
    private String name;
    private String firstName;
    private String lastName;
    private Integer age;
    private String nationality;
    private String height;
    private String weight;
    private Boolean injured;
    private String photoUrl;
    private Integer rapidId;

    public FullPlayerResponse(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.age = player.getAge();
        this.nationality = player.getNationality();
        this.height = player.getHeight();
        this.weight = player.getWeight();
        this.injured = player.getInjured();
        this.photoUrl = player.getPhotoUrl();
        this.rapidId = player.getRapidId();
    }
}
