package org.scoreboard.util;

import org.scoreboard.model.Match;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

public class MatchSummaryComparator implements Comparator<Match>, Serializable {

    @Serial
    private static final long serialVersionUID = 1234567L;

    @Override
    public int compare(Match m1, Match m2) {
        int score1 = m1.getHomeScore() + m1.getAwayScore();
        int score2 = m2.getHomeScore() + m2.getAwayScore();

        if (score1 != score2) {
            return Integer.compare(score2, score1);
        }
        return m2.getStartTime().compareTo(m1.getStartTime());
    }
}
