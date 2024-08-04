package com.example.demo.controller

import com.example.demo.repository.TeamRepository
import com.example.demo.service.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class TeamControllerTest extends Specification {

    def teamService = Mock(TeamService)

    @Autowired
    def teamController = new TeamController(teamService)

    @Autowired
    private TeamRepository teamRepository;

    MockMvc mvc = standaloneSetup(teamController).build()

    static {
        PostgreSQLContainer postgresql = new PostgreSQLContainer("postgres:14-alpine")
                .withDatabaseName("foo")
                .withUsername("foo")
                .withPassword("secret")
        postgresql.withInitScript("schema.sql")
//        postgresql.withInitScript("data.sql")
        postgresql.start();
    }

    def "Get team"() {
        given:
//        def team = new Team(
//                1,
//                "Manchester United",
//                "MUN",
//                "England",
//                1878,
//                false,
//                "https://media.api-sports.io/football/teams/33.png",
//                33,
//                null,
//                null
//        )
//
//        teamRepository.save(team)

        when:
        def response = mvc.perform(get("/api/teams/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()

        then:
        response
    }
}
