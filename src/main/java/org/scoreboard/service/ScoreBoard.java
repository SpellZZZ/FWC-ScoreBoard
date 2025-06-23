package org.scoreboard.service;

public interface ScoreBoard {
    void startGame(String homeTeam, String awayTeam);
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
}
