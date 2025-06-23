package org.scoreboard.repo;

import org.scoreboard.model.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatchRepositoryImpl implements MatchRepository {
    private List<Match> matches = new ArrayList<>();

    @Override
    public void save(Match match) {
        matches.add(match);
    }

    @Override
    public List<Match> findAll() {
        return matches;
    }

    @Override
    public Optional<Match> findByTeams(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst();
    }
}
