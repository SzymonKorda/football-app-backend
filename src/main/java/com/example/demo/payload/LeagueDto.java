package com.example.demo.payload;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDto {
    private Integer id;
    private String name;
    private String type;
    private String logo;
    private Integer rapidId;
}
