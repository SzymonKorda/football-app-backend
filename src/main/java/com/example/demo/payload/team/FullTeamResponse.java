package com.example.demo.payload.team;

import com.example.demo.model.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullTeamResponse {
    private Integer id;
    private String name;
    private String code;
    private String country;
    private Integer founded;
    private Boolean national;
    private String logoUrl;
    private Integer rapidId;

    public FullTeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.code = team.getCode();
        this.country = team.getCountry();
        this.founded = team.getFounded();
        this.national = team.getNational();
        this.logoUrl = team.getLogoUrl();
        this.rapidId = team.getId();
    }
}
