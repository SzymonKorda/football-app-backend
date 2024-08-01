package com.example.demo.model;

import com.example.demo.payload.team.rapid.RapidTeam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "rapidId")
    private Integer rapidId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "leagueId")
    private League league;

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    public Team(RapidTeam rapidTeam, League league) {
        this.name = rapidTeam.name();
        this.code = rapidTeam.code();
        this.country = rapidTeam.country();
        this.founded = rapidTeam.founded();
        this.national = rapidTeam.national();
        this.logoUrl = rapidTeam.logo();
        this.league = league;
        //TODO skorda: Remove this static assignment
        this.rapidId = rapidTeam.id();
    }
}
