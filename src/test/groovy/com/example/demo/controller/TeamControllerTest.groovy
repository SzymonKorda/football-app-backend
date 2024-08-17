package com.example.demo.controller

import com.example.demo.payload.team.api.CreateTeamsRequest
import com.example.demo.payload.team.rapid.RapidTeam
import com.example.demo.payload.team.rapid.RapidTeamInformationResponse
import com.example.demo.payload.team.rapid.RapidTeamResponse
import com.example.demo.webclient.RapidWebClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TeamControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @SpringBean
            RapidWebClient rapidWebClient = Mock()

    @Shared
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14-alpine")
            .withDatabaseName("foo")
            .withUsername("foo")
            .withPassword("secret")

    @Sql(["/league_and_teams_data.sql"])
    @Transactional
    def "Get team"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teams/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value("Manchester United"))
    }

    @Sql(["/league_and_teams_data.sql"])
    @Transactional
    def "Get teams"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teams")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("\$.length()").value(2))
    }

    @Sql(["/league_data.sql"])
    @Transactional
    def "Create teams"() {
        given:
        def rapidTeam1 = new RapidTeam(
                1,
                "Manchester United",
                "MUN",
                "England",
                1878,
                false,
                "https://media.api-sports.io/football/teams/33.png",
                14
        )
        def rapidTeam2 = new RapidTeam(
                2,
                "Newcastle",
                "NEW",
                "England",
                1892,
                false,
                "https://media.api-sports.io/football/teams/34.png",
                14
        )
        def rapidTeamResponse1 = new RapidTeamResponse(rapidTeam1)
        def rapidTeamResponse2 = new RapidTeamResponse(rapidTeam2)
        def rapidTeamInformationResponse = new RapidTeamInformationResponse(
                List.of(rapidTeamResponse1, rapidTeamResponse2)
        )
        rapidWebClient.fetchTeams(39) >> rapidTeamInformationResponse

        def createTeamRequest = new CreateTeamsRequest("Premier League")
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(createTeamRequest);

        expect:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/teams/admin")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("\$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.*.name").value(Matchers.hasItem("Newcastle")))
    }

}

