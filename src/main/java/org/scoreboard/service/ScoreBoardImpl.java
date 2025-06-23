package org.scoreboard.service;

import org.scoreboard.exception.ScoreException;
import org.scoreboard.exception.WCMatchException;
import org.scoreboard.model.Match;
import org.scoreboard.repo.MatchRepository;
import org.scoreboard.util.MatchSummaryComparator;

import java.util.List;

public class ScoreBoardImpl implements ScoreBoard {
    private final MatchRepository matchRepository;

    ScoreBoardImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void startGame(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

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

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        validateTeamNames(homeTeam, awayTeam);
        validateScore(homeScore, awayScore);

        Match match = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(() -> new WCMatchException("Match not found"));

        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        validateTeamNames(homeTeam, awayTeam);

        Match matchToRemove = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(() -> new WCMatchException("Match not found"));

        matchRepository.remove(matchToRemove);
    }

    @Override
    public List<Match> getSummary() {
        return List.copyOf(
                matchRepository.findAll().stream()
                        .sorted(new MatchSummaryComparator())
                        .toList()
        );
    }

    private void validateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new ScoreException("Scores must be non-negative");
        }
    }

    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null || homeTeam.isEmpty() || awayTeam.isEmpty()) {
            throw new WCMatchException("Team names must not be null or empty");
        }
    }
}
