package org.scoreboard.service;

import org.scoreboard.model.Match;

import java.util.List;

public interface ScoreBoard {
    void startGame(String homeTeam, String awayTeam);
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
    void finishGame(String homeTeam, String awayTeam);
    List<Match> getSummary();

}
