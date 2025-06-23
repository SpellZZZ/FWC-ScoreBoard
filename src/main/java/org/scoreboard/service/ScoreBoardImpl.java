package org.scoreboard.service;

import org.scoreboard.exception.WCMatchException;
import org.scoreboard.model.Match;
import org.scoreboard.repo.MatchRepository;

public class ScoreBoardImpl implements ScoreBoard {
    private final MatchRepository matchRepository;

    ScoreBoardImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void startGame(String homeTeam, String awayTeam) {

        if (homeTeam.equals(awayTeam)) {
            throw new WCMatchException("A team cannot play against itself");
        }

        boolean matchExists = matchRepository.findAll().stream()
                .anyMatch(m -> m.getHomeTeam().equals(homeTeam) || m.getAwayTeam().equals(awayTeam));

        if (matchExists) {
            throw new WCMatchException("One of teams already play");
        }

        Match match = new Match(homeTeam, awayTeam);
        matchRepository.save(match);
    }
}
