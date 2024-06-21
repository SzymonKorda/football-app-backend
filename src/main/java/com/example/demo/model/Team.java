package com.example.demo.model;

import com.example.demo.payload.TeamDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "country")
    private String country;

    @Column(name = "founded")
    private Integer founded;

    @Column(name = "national")
    private Boolean national;

    @Column(name = "logoUrl")
    private String logoUrl;

    public Team(TeamDto teamDto) {
        this.name = teamDto.getName();
        this.code = teamDto.getCode();
        this.country = teamDto.getCountry();
        this.founded = teamDto.getFounded();
        this.national = teamDto.getNational();
        this.logoUrl = teamDto.getLogo();
    }
}
