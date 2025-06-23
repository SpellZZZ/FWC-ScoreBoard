package org.scoreboard.service;

import org.scoreboard.repo.MatchRepository;

public class ScoreBoardImpl implements ScoreBoard {
    private final MatchRepository matchRepository;

    ScoreBoardImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

}
