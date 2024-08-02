package com.example.demo.repository;

import com.example.demo.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    boolean existsByName(String leagueName);

    Optional<League> findByName(String leagueName);

    Optional<League> findByRapidId(Integer rapidId);
}
