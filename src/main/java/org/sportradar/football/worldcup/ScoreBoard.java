package org.sportradar.football.worldcup;

import org.sportradar.football.Game;
import org.sportradar.football.worldcup.exceptions.AlreadyExistsException;
import org.sportradar.football.worldcup.exceptions.DoesNotExistsException;
import org.sportradar.football.worldcup.exceptions.InvalidScoreException;
import org.sportradar.football.worldcup.exceptions.InvalidTeamName;

import java.util.List;

public interface ScoreBoard {

    void startGame(String homeTeam, String awayTeam) throws AlreadyExistsException, InvalidTeamName;

    void finishGame(String homeTeam, String awayTeam) throws DoesNotExistsException, InvalidTeamName;

    void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) throws DoesNotExistsException, InvalidTeamName, InvalidScoreException;

    List<Game> getSummaryOfTotalScoreGames();
}
