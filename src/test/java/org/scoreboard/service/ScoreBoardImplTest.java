package org.scoreboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.scoreboard.repo.MatchRepository;
import org.scoreboard.repo.MatchRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardImplTest {

    ScoreBoard scoreBoard;
    MatchRepository matchRepository;

    @BeforeEach
    void setUp() {
        matchRepository = new MatchRepositoryImpl();
        scoreBoard = new ScoreBoardImpl(matchRepository);
    }


}