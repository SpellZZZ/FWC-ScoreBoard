package org.scoreboard.service;

import org.scoreboard.model.Match;
import org.scoreboard.repo.MatchRepository;

public class ScoreBoardImpl implements ScoreBoard {
    private final MatchRepository matchRepository;

    ScoreBoardImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void startGame(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        matchRepository.save(match);
    }
}
