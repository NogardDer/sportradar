package org.sportradar.football.worldcup;

import org.sportradar.football.Game;
import org.sportradar.football.worldcup.exceptions.AlreadyExistsException;
import org.sportradar.football.worldcup.exceptions.DoesNotExistsException;
import org.sportradar.football.worldcup.exceptions.InvalidScoreException;
import org.sportradar.football.worldcup.exceptions.InvalidTeamName;

import java.util.List;

public class ScoreBoardImpl implements ScoreBoard {

    @Override
    public void startGame(String homeTeam, String awayTeam) throws AlreadyExistsException, InvalidTeamName {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) throws DoesNotExistsException, InvalidTeamName {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) throws DoesNotExistsException, InvalidTeamName, InvalidScoreException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<Game> getSummaryOfTotalScoreGames() {
        throw new RuntimeException("Not implemented");
    }
}
