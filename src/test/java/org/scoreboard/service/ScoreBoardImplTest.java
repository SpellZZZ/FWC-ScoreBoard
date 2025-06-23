package org.scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreboard.exception.WCMatchException;
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

        //when
        scoreBoard.startGame(homeTeam, awayTeam);

        //then
        List<Match> summary = matchRepository.findAll();
        assertEquals(expectedListSize, summary.size());

        Match match = summary.getFirst();
        assertEquals(homeTeam, match.getHomeTeam());
        assertEquals(awayTeam, match.getAwayTeam());
    }

    @Test
    void startGameWithMoreThanOneMatchTest() {
        //given
        String firstHomeTeam = "Mexico";
        String firstAwayTeam = "Canada";
        String secondHomeTeam = "Spain";
        String secondAwayTeam = "Brazil";
        int expectedListSize = 2;

        //when
        scoreBoard.startGame(firstHomeTeam, firstAwayTeam);
        scoreBoard.startGame(secondHomeTeam, secondAwayTeam);

        //then
        List<Match> summary = matchRepository.findAll();
        assertEquals(expectedListSize, summary.size());
    }

    @Test
    void startGameSameTeam() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        scoreBoard.startGame(homeTeam, awayTeam);

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.startGame(homeTeam, awayTeam);
        });
    }

}