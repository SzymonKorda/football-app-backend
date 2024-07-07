package com.example.demo.model;

import com.example.demo.payload.LeagueDto;
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
@Table(name = "league")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "logoUrl")
    private String logoUrl;

    @Column(name = "rapidId")
    private Integer rapidId;

    @JsonIgnore
    @OneToMany(mappedBy = "league")
    private List<Team> teams = new ArrayList<>();


    public League(LeagueDto leagueDto) {
        this.name = leagueDto.getName();
        this.type = leagueDto.getType();
        this.logoUrl = leagueDto.getLogo();
        //TODO skorda: Remove this static assignment
        this.rapidId = 39;
    }
}
