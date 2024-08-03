package com.example.demo.service

import com.example.demo.exception.league.LeagueNotFoundException
import com.example.demo.exception.team.TeamAlreadyExistsException
import com.example.demo.exception.team.TeamNotFoundException
import com.example.demo.model.League
import com.example.demo.model.Team
import com.example.demo.payload.team.rapid.RapidTeam
import com.example.demo.payload.team.rapid.RapidTeamInformationResponse
import com.example.demo.payload.team.rapid.RapidTeamResponse
import com.example.demo.repository.LeagueRepository
import com.example.demo.repository.TeamRepository
import com.example.demo.webclient.RapidWebClient
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Specification

class TeamServiceTest extends Specification {

    def teamRepository = Mock(TeamRepository)
    def leagueRepository = Mock(LeagueRepository)
    def rapidWebClient = Mock(RapidWebClient)

    @Autowired
    def teamService = new TeamService(teamRepository, leagueRepository, rapidWebClient)

    @Shared
    def teamId = 1;
    def rapidId = 1;
    def LEAGUE_NAME = "Premier League"

    def "Should retrieve team"() {
        given:
        def team = Mock(Team)
        teamRepository.findById(teamId) >> Optional.of(team)

        when:
        teamService.retrieveTeam(teamId)

        then:
        noExceptionThrown()
    }

    def "Should throw exception when team doesn't exist"() {
        given:
        teamRepository.findById(teamId) >> Optional.empty()

        when:
        teamService.retrieveTeam(teamId)

        then:
        def e = thrown(TeamNotFoundException)
        e.message == "Team doesn't exist"
    }

    def "Should retrieve teams"() {
        when:
        teamService.retrieveTeams()

        then:
        1 * teamRepository.findAll()
    }

    def "Should create teams"() {
        given:
        def league = Mock(League, {
            it.rapidId >> rapidId
        })
        def rapidTeamInformationResponse = prepareRapidTeamInformationResponse()

        1 * leagueRepository.findByName(LEAGUE_NAME) >> Optional.of(league)
        1 * rapidWebClient.fetchTeams(rapidId) >> rapidTeamInformationResponse
        3 * teamRepository.existsByRapidId(_) >> false

        when:
        teamService.createTeams(LEAGUE_NAME)

        then:
        1 * teamRepository.saveAll(_)
    }

    def "Should throw exception during create teams when league doesn't exist"() {
        given:
        1 * leagueRepository.findByName(LEAGUE_NAME) >> Optional.empty()

        when:
        teamService.createTeams(LEAGUE_NAME)

        then:
        def e = thrown(LeagueNotFoundException)
        e.message == "League doesn't exist"
    }

    def "Should throw exception during create teams when one of the teams already exists"() {
        given:
        def league = Mock(League, {
            it.rapidId >> rapidId
        })
        def rapidTeamInformationResponse = prepareRapidTeamInformationResponse()

        1 * leagueRepository.findByName(LEAGUE_NAME) >> Optional.of(league)
        1 * rapidWebClient.fetchTeams(rapidId) >> rapidTeamInformationResponse
        1 * teamRepository.existsByRapidId(_) >> true

        when:
        teamService.createTeams(LEAGUE_NAME)

        then:
        def e = thrown(TeamAlreadyExistsException)
        e.message == "Team already exists"
    }

    private static RapidTeamInformationResponse prepareRapidTeamInformationResponse() {
        def rapidTeam1 = prepareRapidTeam(1)
        def rapidTeam2 = prepareRapidTeam(2)
        def rapidTeam3 = prepareRapidTeam(3)

        def rapidTeamResponse1 = new RapidTeamResponse(rapidTeam1)
        def rapidTeamResponse2 = new RapidTeamResponse(rapidTeam2)
        def rapidTeamResponse3 = new RapidTeamResponse(rapidTeam3)

        return new RapidTeamInformationResponse(List.of(
                rapidTeamResponse1, rapidTeamResponse2, rapidTeamResponse3))
    }

    private static RapidTeam prepareRapidTeam(Integer rapidId) {
        new RapidTeam(null, null, null, null, null, null, null, rapidId)
    }
}
