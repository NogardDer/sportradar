package org.sportradar.football.worldcup;

import org.sportradar.football.worldcup.model.Game;
import org.sportradar.football.worldcup.exceptions.AlreadyExistsException;
import org.sportradar.football.worldcup.exceptions.DoesNotExistsException;
import org.sportradar.football.worldcup.exceptions.InvalidScoreException;
import org.sportradar.football.worldcup.exceptions.InvalidTeamNameException;

import java.util.List;

public interface ScoreBoard {

    void startGame(String homeTeam, String awayTeam) throws AlreadyExistsException, InvalidTeamNameException;

    void finishGame(String homeTeam, String awayTeam) throws DoesNotExistsException, InvalidTeamNameException;

    void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) throws DoesNotExistsException, InvalidTeamNameException, InvalidScoreException;

    List<Game> getSummaryOfTotalScoreGames();
}
