package com.example.demo.repository;

import com.example.demo.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean existsByRapidId(Integer rapidId);
    List<Team> findAllByRapidId(Integer rapidId);
}
