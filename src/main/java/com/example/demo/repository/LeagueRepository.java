package com.example.demo.repository;

import com.example.demo.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    boolean existsByRapidId(Integer rapidId);

    Optional<League> findByName(String leagueName);

    Optional<League> findById(Integer leagueId);

    Optional<League> findByRapidId(Integer rapidId);
}
