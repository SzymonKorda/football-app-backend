package com.example.demo.payload.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
    private PlayerDto player;
    private List<PlayerStatistics> statistics;
}
