package com.example.demo.repository;

import com.example.demo.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    boolean existsByRapidId(Integer rapidId);
    League findByRapidId(Integer rapidId);
}
