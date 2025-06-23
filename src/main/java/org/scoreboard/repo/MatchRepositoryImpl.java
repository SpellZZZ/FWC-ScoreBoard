package org.scoreboard.repo;

import org.scoreboard.model.Match;

import java.util.ArrayList;
import java.util.List;

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
}
