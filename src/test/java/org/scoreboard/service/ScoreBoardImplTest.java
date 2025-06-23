package org.scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreboard.exception.ScoreException;
import org.scoreboard.exception.WCMatchException;
import org.scoreboard.model.Match;
import org.scoreboard.repo.MatchRepository;
import org.scoreboard.repo.MatchRepositoryImpl;

import java.time.LocalDateTime;
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
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int homeScore = 3;
        int awayScore = 2;
        scoreBoard.startGame(homeTeam, awayTeam);

        //when
        scoreBoard.updateScore("Mexico", "Canada", homeScore, awayScore);

        //then
        Match match = matchRepository.findAll().getFirst();
        assertEquals(homeScore, match.getHomeScore());
        assertEquals(awayScore, match.getAwayScore());
    }

    @Test
    void updateScoreThrowsIfMatchNotFound() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int homeScore = 3;
        int awayScore = 2;

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        });
    }

    @Test
    void updateScoreThrowsOnNegativeScore() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int homeScore = -3;
        int awayScore = -2;
        scoreBoard.startGame(homeTeam, awayTeam);

        //when then
        assertThrows(ScoreException.class, () -> {
            scoreBoard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        });
    }

    @Test
    void updateScoreThrowsOnNegativeScoreNegativeFirst() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int homeScore = -3;
        int awayScore = 2;
        scoreBoard.startGame(homeTeam, awayTeam);

        //when then
        assertThrows(ScoreException.class, () -> {
            scoreBoard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        });
    }

    @Test
    void updateScoreThrowsOnNegativeScoreSecond() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int homeScore = 3;
        int awayScore = -2;
        scoreBoard.startGame(homeTeam, awayTeam);

        //when then
        assertThrows(ScoreException.class, () -> {
            scoreBoard.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        });
    }

    @Test
    void updateScoreThrowsIfTeamsEmpty() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        String nullTeam = "";
        int homeScore = 3;
        int awayScore = 2;

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.updateScore(nullTeam, awayTeam, homeScore, awayScore);
        });

        assertThrows(WCMatchException.class, () -> {
            scoreBoard.updateScore(homeTeam, nullTeam, homeScore, awayScore);
        });
    }

    @Test
    void updateScoreThrowsIfTeamsNull() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        String nullTeam = null;
        int homeScore = 3;
        int awayScore = 2;

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.updateScore(nullTeam, awayTeam, homeScore, awayScore);
        });

        assertThrows(WCMatchException.class, () -> {
            scoreBoard.updateScore(homeTeam, nullTeam, homeScore, awayScore);
        });
    }

    @Test
    void updateScoreCanHandleMultipleMatches() {
        // given
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";
        int newHomeScore = 3;
        int newAwayScore = 2;

        scoreBoard.startGame(homeTeam1, awayTeam1);
        scoreBoard.startGame(homeTeam2, awayTeam2);

        // when
        scoreBoard.updateScore(homeTeam2, awayTeam2, newHomeScore, newAwayScore);

        // then
        Match updatedMatch = matchRepository.findByTeams(homeTeam2, awayTeam2)
                .orElseThrow(() -> new WCMatchException("Match not found"));

        assertEquals(newHomeScore, updatedMatch.getHomeScore());
        assertEquals(newAwayScore, updatedMatch.getAwayScore());
    }

    @Test
    void finishGameRemovesMatchFromList() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int expectedRepoSize = 0;
        scoreBoard.startGame(homeTeam, awayTeam);

        //when
        scoreBoard.finishGame(homeTeam, awayTeam);

        //then
        assertEquals(expectedRepoSize, matchRepository.findAll().size());
    }

    @Test
    void finishGameThrowsIfMatchNotFound() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.finishGame(homeTeam, awayTeam);
        });
    }
    @Test
    void finishGameThrowsIfTeamNamesAreNull() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        String nullTeam = null;

        //when then
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.finishGame(nullTeam, awayTeam);
        });
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.finishGame(homeTeam, nullTeam);
        });
    }

    @Test
    void finishGameThrowsIfTeamNamesAreEmpty() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        String emptyTeam = "";

        assertThrows(WCMatchException.class, () -> {
            scoreBoard.finishGame(emptyTeam, awayTeam);
        });
        assertThrows(WCMatchException.class, () -> {
            scoreBoard.finishGame(homeTeam, emptyTeam);
        });
    }

    @Test
    void finishGameOnlyRemovesExactMatch() {
        //given
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";
        int expectedSize = 1;

        scoreBoard.startGame(homeTeam1, awayTeam1);
        scoreBoard.startGame(homeTeam2, awayTeam2);

        //when
        scoreBoard.finishGame(homeTeam2, awayTeam2);

        //then
        List<Match> matches = matchRepository.findAll();
        assertEquals(expectedSize, matches.size());
        assertEquals(homeTeam1, matches.getFirst().getHomeTeam());
        assertEquals(awayTeam1, matches.getFirst().getAwayTeam());
    }

    @Test
    void getSummarySortsByTotalScoreDescending() {
        //given
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";

        scoreBoard.startGame(homeTeam1, awayTeam1);
        scoreBoard.startGame(homeTeam2, awayTeam2);

        //when
        scoreBoard.updateScore(homeTeam1, awayTeam1, 1, 1); // 2
        scoreBoard.updateScore(homeTeam2, awayTeam2, 3, 2); // 5

        //then
        List<Match> summary = scoreBoard.getSummary();

        assertEquals(homeTeam2, summary.getFirst().getHomeTeam());
        assertEquals(awayTeam2, summary.getFirst().getAwayTeam());

        assertEquals(homeTeam1, summary.getLast().getHomeTeam());
        assertEquals(awayTeam1, summary.getLast().getAwayTeam());
    }

    @Test
    void getSummarySortsByRecencyIfScoreEqual() {
        //given
        String homeTeam1 = "Mexico";
        String awayTeam1 = "Canada";
        String homeTeam2 = "Spain";
        String awayTeam2 = "Brazil";
        Match olderMatch = createAndStartMatch(homeTeam1, awayTeam1, 2, 2, LocalDateTime.now().minusSeconds(10));
        Match newerMatch = createAndStartMatch(homeTeam2, awayTeam2, 2, 2, LocalDateTime.now());

        // when
        List<Match> summary = scoreBoard.getSummary();

        // then
        assertEquals(newerMatch.getHomeTeam(), summary.getFirst().getHomeTeam());
        assertEquals(newerMatch.getAwayTeam(), summary.getFirst().getAwayTeam());
        assertEquals(olderMatch.getHomeTeam(), summary.getLast().getHomeTeam());
        assertEquals(olderMatch.getAwayTeam(), summary.getLast().getAwayTeam());
    }

    private Match createAndStartMatch(String home, String away, int homeScore, int awayScore, LocalDateTime startTime) {
        scoreBoard.startGame(home, away);
        scoreBoard.updateScore(home, away, homeScore, awayScore);

        Match match = matchRepository.findByTeams(home, away)
                .orElseThrow(() -> new IllegalStateException("Match not found"));

        match.setStartTime(startTime);
        return match;
    }

    @Test
    void getSummaryReturnsCopyOfList() {
        //given
        String homeTeam = "Mexico";
        String awayTeam = "Canada";
        int expectedSize = 1;
        scoreBoard.startGame(homeTeam, awayTeam);
        List<Match> summary = scoreBoard.getSummary();

        //when then
        assertThrows(UnsupportedOperationException.class, summary::clear);
        assertEquals( expectedSize, scoreBoard.getSummary().size());

    }
}