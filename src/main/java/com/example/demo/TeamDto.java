package com.example.demo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private Integer id;

    private String name;

    private String code;

    private String country;

    private Integer founded;

    private Boolean national;

    private String logo;

}