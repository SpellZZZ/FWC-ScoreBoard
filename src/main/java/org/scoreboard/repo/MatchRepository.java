package org.scoreboard.repo;

import org.scoreboard.model.Match;

import java.util.List;

public interface MatchRepository {
    void save(Match match);
    List<Match> findAll();
}
