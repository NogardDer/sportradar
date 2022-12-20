package org.sportradar.football.worldcup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sportradar.football.Game;
import org.sportradar.football.worldcup.exceptions.AlreadyExistsException;
import org.sportradar.football.worldcup.exceptions.DoesNotExistsException;
import org.sportradar.football.worldcup.exceptions.InvalidScoreException;
import org.sportradar.football.worldcup.exceptions.InvalidTeamName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreBoardTest {
    ScoreBoard scoreBoard;

    @BeforeEach
    void before() {
        scoreBoard = new ScoreBoardImpl();
    }

    @Test
    void testStartGame() throws AlreadyExistsException, InvalidTeamName {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";

        scoreBoard.startGame(homeTeam, awayTeam);

        List<Game> result = scoreBoard.getSummaryOfTotalScoreGames();

        assertEquals(1, result.size());
        assertEquals(new Game(homeTeam, awayTeam), result.get(1));
    }

    static Stream<String> invalidTeamName() {
        return Stream.of("", "  ", ".", "-", "[", null);
    }

    @Test
    void testStartGameAlreadyExists() throws AlreadyExistsException, InvalidTeamName {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";
        scoreBoard.startGame(homeTeam, awayTeam);

        AlreadyExistsException thrown = assertThrows(AlreadyExistsException.class, () ->
                scoreBoard.startGame(homeTeam, awayTeam)
        );
        assertEquals(String.format("Game %s - %s is already exists", homeTeam, awayTeam), thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidTeamName")
    void testStartGameInvalidHomeTeamName(String homeTeam) {
        String awayTeam = "awayTeam";

        InvalidTeamName thrown = assertThrows(InvalidTeamName.class, () ->
                scoreBoard.startGame(homeTeam, awayTeam)
        );
        assertEquals(String.format("Team name:[%s] is not valid", homeTeam), thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidTeamName")
    void testStartGameInvalidAwayTeamName(String awayTeam) {
        String homeTeam = "homeTeam";

        InvalidTeamName thrown = assertThrows(InvalidTeamName.class, () ->
                scoreBoard.startGame(homeTeam, awayTeam)
        );
        assertEquals(String.format("Team name:[%s] is not valid", awayTeam), thrown.getMessage());
    }

    @Test
    void testFinishGame() throws AlreadyExistsException, InvalidTeamName, DoesNotExistsException {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";

        scoreBoard.startGame(homeTeam, awayTeam);
        scoreBoard.finishGame(homeTeam, awayTeam);

        List<Game> result = scoreBoard.getSummaryOfTotalScoreGames();

        assertEquals(0, result.size());
    }

    @Test
    void testFinishGameGameDoesNotExist() {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";

        DoesNotExistsException thrown = assertThrows(DoesNotExistsException.class, () ->
                scoreBoard.finishGame(homeTeam, awayTeam)
        );
        assertEquals(String.format("Game %s - %s does not exists", homeTeam, awayTeam), thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidTeamName")
    void testFinishGameInvalidHomeTeamName(String homeTeam) {
        String awayTeam = "awayTeam";

        InvalidTeamName thrown = assertThrows(InvalidTeamName.class, () ->
                scoreBoard.finishGame(homeTeam, awayTeam)
        );
        assertEquals(String.format("Team name:[%s] is not valid", homeTeam), thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidTeamName")
    void testFinishGameInvalidAwayTeamName(String awayTeam) {
        String homeTeam = "homeTeam";

        InvalidTeamName thrown = assertThrows(InvalidTeamName.class, () ->
                scoreBoard.finishGame(homeTeam, awayTeam)
        );
        assertEquals(String.format("Team name:[%s] is not valid", awayTeam), thrown.getMessage());
    }

    @Test
    void testUpdateScore() throws AlreadyExistsException, InvalidTeamName, InvalidScoreException, DoesNotExistsException {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";
        int homeScore = 1;
        int awayScore = 2;

        scoreBoard.startGame(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, homeScore, awayTeam, awayScore);

        List<Game> result = scoreBoard.getSummaryOfTotalScoreGames();

        assertEquals(1, result.size());
        Game expectedGame = createGame(homeTeam, homeScore, awayTeam, awayScore);
        assertEquals(expectedGame, result.get(1));
    }

    @Test
    void testUpdateGameGameDoesNotExist() {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";
        int homeScore = 1;
        int awayScore = 2;

        DoesNotExistsException thrown = assertThrows(DoesNotExistsException.class, () ->
                scoreBoard.updateScore(homeTeam, homeScore, awayTeam, awayScore)
        );
        assertEquals(String.format("Game %s - %s does not exists", homeTeam, awayTeam), thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidTeamName")
    void testUpdateGameInvalidHomeTeamName(String homeTeam) {
        String awayTeam = "awayTeam";
        int homeScore = 1;
        int awayScore = 2;

        InvalidTeamName thrown = assertThrows(InvalidTeamName.class, () ->
                scoreBoard.updateScore(homeTeam, homeScore, awayTeam, awayScore)
        );
        assertEquals(String.format("Team name:[%s] is not valid", homeTeam), thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidTeamName")
    void testUpdateGameInvalidAwayTeamName(String awayTeam) {
        String homeTeam = "homeTeam";
        int homeScore = 1;
        int awayScore = 2;

        InvalidTeamName thrown = assertThrows(InvalidTeamName.class, () ->
                scoreBoard.updateScore(homeTeam, homeScore, awayTeam, awayScore)
        );
        assertEquals(String.format("Team name:[%s] is not valid", awayTeam), thrown.getMessage());
    }

    static Stream<Integer> invalidScore() {
        return Stream.of(-5, -1, 20, 100);
    }

    @ParameterizedTest
    @MethodSource("invalidScore")
    void testUpdateGameInvalidHomeScore(int homeScore) throws AlreadyExistsException, InvalidTeamName {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";
        int awayScore = 2;
        scoreBoard.startGame(homeTeam, awayTeam);

        InvalidScoreException thrown = assertThrows(InvalidScoreException.class, () ->
                scoreBoard.updateScore(homeTeam, homeScore, awayTeam, awayScore)
        );
        assertEquals(String.format("Score:[%s] is not valid", homeScore), thrown.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidScore")
    void testUpdateGameInvalidAwayScore(int awayScore) throws AlreadyExistsException, InvalidTeamName {
        String homeTeam = "homeTeam";
        String awayTeam = "awayTeam";
        int homeScore = 1;
        scoreBoard.startGame(homeTeam, awayTeam);

        InvalidScoreException thrown = assertThrows(InvalidScoreException.class, () ->
                scoreBoard.updateScore(homeTeam, homeScore, awayTeam, awayScore)
        );
        assertEquals(String.format("Score:[%s] is not valid", awayScore), thrown.getMessage());
    }

    @Test
    void testGetSummaryOfTotalScoreGames() throws AlreadyExistsException, InvalidTeamName, InvalidScoreException, DoesNotExistsException {
        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.startGame("Germany", "France");

        scoreBoard.updateScore("Mexico", 0, "Canada", 5);
        scoreBoard.updateScore("Spain", 10, "Brazil", 2);
        scoreBoard.updateScore("Germany", 2, "France", 2);
        scoreBoard.updateScore("Uruguay", 6, "Italy", 6);
        scoreBoard.updateScore("Argentina", 3, "Australia", 1);

        List<Game> result = scoreBoard.getSummaryOfTotalScoreGames();

        List<Game> expectedGames = Arrays.asList(
                createGame("Uruguay", 6, "Italy", 6),
                createGame("Spain", 10, "Brazil", 2),
                createGame("Mexico", 0, "Canada", 5),
                createGame("Argentina", 3, "Australia", 1),
                createGame("Germany", 2, "France", 2)
        );
        assertEquals(expectedGames, result);
    }

    private Game createGame(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        Game expectedGame = new Game(homeTeam, awayTeam);
        expectedGame.setHomeScore(homeScore);
        expectedGame.setAwayScore(awayScore);
        return expectedGame;
    }
}
