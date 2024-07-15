package com.example.demo.model;

import com.example.demo.payload.player.PlayerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "injured")
    private Boolean injured;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "rapidId", unique = true)
    private Integer rapidId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    public Player(PlayerDto playerDto) {
        this.name = playerDto.getName();
        this.firstName = playerDto.getFirstname();
        this.lastName = playerDto.getLastname();
        this.age = playerDto.getAge();
        this.nationality = playerDto.getNationality();
        this.height = playerDto.getHeight();
        this.weight = playerDto.getWeight();
        this.injured = playerDto.getInjured();
        this.photoUrl = playerDto.getPhoto();
        this.rapidId = playerDto.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(rapidId, player.rapidId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rapidId);
    }
}
