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

    @Test
    void startGameOneTeamAlreadyInGame() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        String homeTeamNd = "Poland";
        String awayTeamNd = "Canada";
        scoreBoard.startGame(homeTeam, awayTeam);

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.startGame(homeTeamNd, awayTeamNd);
        });
    }

    @Test
    void startGameTeamPlayWithItself() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Mexico";

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.startGame(homeTeam, awayTeam);
        });
    }

    @Test
    void startGameBlankName() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "";

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.startGame(homeTeam, awayTeam);
        });
    }

    @Test
    void startGameNullAsName() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = null;

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.startGame(homeTeam, awayTeam);
        });
    }

    @Test
    void startGameCheckScore() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int expectedHomeScore = 0;
        int expectedAwayScore = 0;

        //when
        scoreBoard.startGame(homeTeam, awayTeam);

        //then
        List<Match> summary = matchRepository.findAll();

        Match match = summary.getFirst();
        assertEquals(expectedHomeScore, match.getHomeScore());
        assertEquals(expectedAwayScore, match.getAwayScore());
    }

    @Test
    void updateScoreUpdatesScoreCorrectly() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 3, 2);

        Match match = matchRepository.findAll().getFirst();
        assertEquals(3, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

}