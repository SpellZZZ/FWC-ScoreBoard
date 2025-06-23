package org.scoreboard.repo;

import org.scoreboard.model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void save(Match match);
    List<Match> findAll();
    Optional<Match> findByTeams(String homeTeam, String awayTeam);
}
