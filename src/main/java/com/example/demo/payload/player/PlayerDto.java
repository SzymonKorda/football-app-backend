package com.example.demo.payload.player;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private Integer id;
    private String name;
    private String firstname;
    private String lastname;
    private Integer age;
    private String nationality;
    private String height;
    private String weight;
    private Boolean injured;
    private String photo;
}
