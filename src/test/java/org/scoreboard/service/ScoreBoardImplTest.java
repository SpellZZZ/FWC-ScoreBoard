package org.scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreboard.model.Match;
import org.scoreboard.repo.MatchRepository;
import org.scoreboard.repo.MatchRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardImplTest {

    ScoreBoard scoreBoard;
    MatchRepository matchRepository;

    @BeforeEach
    void setUp() {
        matchRepository = new MatchRepositoryImpl();
        scoreBoard = new ScoreBoardImpl(matchRepository);
    }

    @Test
    void startGame() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int expectedListSize = 1;
        int homeTeamExpectedScore = 0;
        int awayTeamExpectedScore = 0;

        //when
        scoreBoard.startGame(homeTeam, awayTeam);

        //then
        List<Match> summary = matchRepository.findAll();
        assertEquals(expectedListSize, summary.size());

        Match match = summary.getFirst();
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
        assertEquals(homeTeamExpectedScore, match.getHomeScore());
        assertEquals(awayTeamExpectedScore, match.getAwayScore());
    }

}